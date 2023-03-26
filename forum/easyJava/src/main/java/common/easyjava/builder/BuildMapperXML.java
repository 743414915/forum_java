package common.easyjava.builder;

import common.easyjava.bean.Constants;
import common.easyjava.bean.FieldInfo;
import common.easyjava.bean.TableInfo;
import common.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildMapperXML {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildMapper.class);

    private static final String BASE_COLUMN_LIST = "base_column_list";

    private static final String BASE_QUERY_CONDITION = "base_query_condition";

    private static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";

    private static final String QUERY_CONDITION = "query_condition";

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_MAPPERS_XLMS);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPERS;
        File poFile = new File(folder, className + ".xml");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
            bw.newLine();
            bw.write("\t\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            bw.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPERS + "." + className + "\">");
            bw.newLine();

            bw.write("\t<!--实体映射-->");
            bw.newLine();
            String poClass = Constants.PACKAGE_PO + "." + tableInfo.getBeanName();
            bw.write("\t<resultMap id=\"base_result_map\" type=\"" + poClass + "\">");
            bw.newLine();

            FieldInfo idField = null;
            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                if ("PRIMARY".equals(entry.getKey())) {
                    List<FieldInfo> fieldInfoList = entry.getValue();
                    if (fieldInfoList.size() == 1) {
                        idField = fieldInfoList.get(0);
                        break;
                    }
                }
            }
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t<!--" + fieldInfo.getComment() + "-->");
                bw.newLine();
                String key = "";
                if (idField != null && fieldInfo.getPropertyName().equals(idField.getPropertyName())) {
                    key = "id";
                } else {
                    key = "result";
                }
                bw.write("\t\t<" + key + " column=\"" + fieldInfo.getFieldName() + "\" property=\"" + fieldInfo.getPropertyName() + "\"/>");
                bw.newLine();
            }

            bw.write("\t</resultMap>");
            bw.newLine();
            bw.newLine();

            // 通用查询列
            bw.write("\t<!--通用查询结果列-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_COLUMN_LIST + "\">");
            bw.newLine();
            StringBuilder columnBuilder = new StringBuilder();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                columnBuilder.append(fieldInfo.getFieldName()).append(",");
            }
            String columnBuilderStr = columnBuilder.substring(0, columnBuilder.lastIndexOf(","));
            bw.write("\t\t" + columnBuilderStr + "");
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            // 基础查询条件
            bw.write("\t<!--基础查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION + "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String stringQuery = "";
                String propertyName = fieldInfo.getPropertyName();
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                    stringQuery = " and query." + propertyName + " != null";
                }
                bw.write("\t\t<if test=\"query." + propertyName + " != null" + stringQuery + "\">");
                bw.newLine();
                bw.write("\t\t\tand id = #{query." + propertyName + "}");
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            // 拓展查询条件
            bw.write("\t<!--拓展查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + BASE_QUERY_CONDITION_EXTEND + "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()) {
                String andWhere = "";
                String propertyName = fieldInfo.getPropertyName();
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                    andWhere = "and " + fieldInfo.getFieldName() + " like concat('%', #{query." + propertyName + "}, '%')";
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()))
                    if (propertyName.endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_START)) {
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + propertyName + "}, '%Y-%m-%d') ]]>";
                    } else if (propertyName.endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_END)) {
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " < date_sub(str_to_date(#{query." + propertyName + "}, '%Y-%m-%d'),interval -1 day) ]]>";
                    }
                bw.write("\t\t<if test=\"query." + propertyName + " != null and query." + propertyName + "\">");
                bw.newLine();
                bw.write("\t\t\t" + andWhere);
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            // 通用查询条件
            bw.write("\t<!--通用查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\"" + QUERY_CONDITION + "\">");
            bw.newLine();
            bw.write("\t\t<where>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_EXTEND + "\"/>");
            bw.newLine();
            bw.write("\t\t</where>");
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            // 查询列表
            bw.write("\t<!--查询列表-->");
            bw.newLine();
            bw.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">");
            bw.newLine();
            bw.write("\t\tSELECT");
            bw.newLine();
            bw.write("\t\t<include refid=\"" + BASE_COLUMN_LIST + "\"/>");
            bw.newLine();
            bw.write("\t\t\tFROM " + tableInfo.getTableName());
            bw.newLine();
            bw.write("\t\t<include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();

            bw.write("\t\t<if test=\"query.orderBy!=null\">");
            bw.newLine();
            bw.write("\t\t\tORDER BY #{query.orderBy}");
            bw.newLine();
            bw.write("\t\t</if>");

            bw.newLine();
            bw.write("\t\t<if test=\"query.simplePage!=null\">");
            bw.newLine();
            bw.write("\t\t\tLIMIT #{query.simplePage.start}, #{query.simplePage.end}");
            bw.newLine();
            bw.write("\t\t</if>");
            bw.newLine();

            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            // 查询数量
            bw.write("\t<!--查询数量-->");
            bw.newLine();
            bw.write("\t<select id=\"selectCount\" resultType=\"java.lang.Integer\">");
            bw.newLine();
            bw.write("\t\tSELECT COUNT(1) FROM " + tableInfo.getTableName());
            bw.newLine();
            bw.write("\t\t<include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            // 插入（匹配有值的字段）
            bw.write("\t<!--插入（匹配有值的字段）-->");
            bw.newLine();
            bw.write("\t<insert id=\"insert\" parameterType=\"" + poClass + "\">");
            bw.newLine();

            FieldInfo autoIncrementDiled = null;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getAutoIncrement() != null && fieldInfo.getAutoIncrement()) {
                    autoIncrementDiled = fieldInfo;
                    break;
                }
            }
            if (autoIncrementDiled != null) {
                bw.write("\t\t<selectKey keyProperty=\"bean." + autoIncrementDiled.getFieldName() + "\" resultType=\"" + autoIncrementDiled.getJavaType() + "\" order=\"AFTER\">");
                bw.newLine();
                bw.write("\t\t\tSELECT LAST_INSERT_ID()");
                bw.newLine();
                bw.write("\t\t</selectKey>");
                bw.newLine();
            }
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName());
            bw.newLine();

            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            // 插入或者更新（匹配有值的字段）
            bw.write("\t<!--插入或者更新（匹配有值的字段）-->");
            bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdate\" parameterType=\"" + poClass + "\">");
            bw.newLine();
            bw.write("\t\tINSERT INTO " + tableInfo.getTableName());
            bw.newLine();

            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t on DUPLICATE key update");
            bw.newLine();

            Map<String, String> keyTempMap = new HashMap<>();
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> fieldInfoList = entry.getValue();
                for (FieldInfo fieldInfo : fieldInfoList) {
                    keyTempMap.put(fieldInfo.getFieldName(), fieldInfo.getFieldName());
                }
            }

            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (keyTempMap.get(fieldInfo.getFieldName()) != null) {
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "),");
                bw.newLine();
                bw.write("\t\t\t</if>");
                bw.newLine();
            }
            bw.write("\t\t</trim>");
            bw.newLine();

            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            // 批量插入
            bw.write("\t<!--批量插入-->");
            bw.newLine();
            bw.write("\t<insert id=\"insertBatch\" parameterType=\"" + poClass + "\">");
            bw.newLine();
            StringBuffer inserFiledBuffer = new StringBuffer();
            StringBuffer inserPropertyBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (!fieldInfo.getAutoIncrement()) {
                    inserFiledBuffer.append(fieldInfo.getFieldName()).append(",");
                    inserPropertyBuffer.append("#{item." + fieldInfo.getPropertyName() + "}").append(",");
                }
            }
            String inserFiledBufferStr = inserFiledBuffer.substring(0, inserFiledBuffer.lastIndexOf(","));
            bw.write("\t\tINSERT INTO");
            bw.newLine();
            bw.write("\t\t" + tableInfo.getTableName() + "(" + inserFiledBufferStr + ")values");
            bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
            bw.newLine();
            String inserPropertyBufferStr = inserPropertyBuffer.substring(0, inserPropertyBuffer.lastIndexOf(","));
            bw.write("\t\t\t(" + inserPropertyBufferStr + ")");
            bw.newLine();
            bw.write("\t\t</foreach>");
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            // 批量插入或更新
            bw.write("\t<!--批量插入或更新-->");
            bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdateBatch\" parameterType=\"" + poClass + "\">");
            bw.newLine();

            inserFiledBufferStr = inserFiledBuffer.substring(0, inserFiledBuffer.lastIndexOf(","));
            bw.write("\t\tINSERT INTO");
            bw.newLine();
            bw.write("\t\t" + tableInfo.getTableName() + "(" + inserFiledBufferStr + ")values");
            bw.newLine();
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
            bw.newLine();

            inserPropertyBufferStr = inserPropertyBuffer.substring(0, inserPropertyBuffer.lastIndexOf(","));
            bw.write("\t\t\t(" + inserPropertyBufferStr + ")");
            bw.newLine();
            bw.write("\t\t</foreach>");
            bw.newLine();
            bw.write("\t\ton DUPLICATE key update");
            bw.newLine();
            StringBuffer insertBatchUpdateBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                insertBatchUpdateBuffer.append("\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "),\n");
            }
            String insertBatchUpdateBufferStr = insertBatchUpdateBuffer.substring(0, insertBatchUpdateBuffer.lastIndexOf(","));
            bw.write(insertBatchUpdateBufferStr);
            bw.newLine();
            bw.write("\t</insert>");
            bw.newLine();
            bw.newLine();

            // 根据主键更新
            for (Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuffer paramSNames = new StringBuffer();

                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    paramSNames.append(fieldInfo.getFieldName() + " = #{" + fieldInfo.getPropertyName() + "}");
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        paramSNames.append("\n\t\t and ");
                    }
                }
                bw.newLine();
                // 查询
                bw.write("\t<!--根据" + methodName + "查询-->");
                bw.newLine();
                bw.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\">");
                bw.newLine();

                bw.write("\t\tselect");
                bw.newLine();
                bw.write("\t\t<include refid=\"" + BASE_COLUMN_LIST + "\"/>");
                bw.newLine();
                bw.write("\t\tfrom " + tableInfo.getTableName());
                bw.newLine();
                bw.write("\t\twhere " + paramSNames);
                bw.newLine();
                bw.write("\t</select>");
                bw.newLine();
                bw.newLine();

                // 更新
                bw.write("\t<!--根据" + methodName + "更新-->");
                bw.newLine();
                bw.write("\t<update id=\"updateBy" + methodName + "\" parameterType=\"" + poClass + "\">");
                bw.newLine();
                bw.write("\t\tupdate " + tableInfo.getTableName());
                bw.newLine();
                bw.write("\t\t<set>");
                bw.newLine();

                for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
//                    if (keyTempMap.get(fieldInfo.getFieldName()) != null) {
//                        continue;
//                    }
                    bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                    bw.newLine();
                    bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = #{bean." + fieldInfo.getPropertyName() + "},");
                    bw.newLine();
                    bw.write("\t\t\t</if>");
                    bw.newLine();
                }

                bw.write("\t\t</set>");
                bw.newLine();
                bw.write("\t\twhere " + paramSNames);
                bw.newLine();
                bw.write("\t</update>");
                bw.newLine();
                bw.newLine();

//                // 删除
                bw.write("\t<!--根据" + methodName + "删除-->");
                bw.newLine();
                bw.write("\t<delete id=\"deleteBy" + methodName + "\">");
                bw.newLine();
                bw.write("\t\tdelete");
                bw.newLine();
                bw.write("\t\tfrom " + tableInfo.getTableName());
                bw.newLine();
                bw.write("\t\twhere " + paramSNames);
                bw.newLine();
                bw.write("\t</delete>");
                bw.newLine();
                bw.newLine();
            }

            bw.write("</mapper>");
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            LOGGER.error("创建mapperXML失败！");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
