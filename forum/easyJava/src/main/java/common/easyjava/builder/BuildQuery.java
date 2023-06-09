package common.easyjava.builder;

import common.easyjava.bean.Constants;
import common.easyjava.bean.FieldInfo;
import common.easyjava.bean.TableInfo;
import common.easyjava.utils.DateUtils;
import common.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BuildQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildQuery.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;
        File queryFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(queryFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();

            if (tableInfo.getHaveBigDecimal()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }

            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bw.write("import java.util.Date;");
                bw.newLine();
            }
            bw.newLine();

            // 构建类注释
            BuildComment.createClassComment(bw, tableInfo.getComment() + " 查询对象");
            bw.write("public class " + className + " extends BaseQuery {");
            bw.newLine();

            for (FieldInfo field : tableInfo.getFieldList()) {
                BuildComment.createFieldComment(bw, field.getComment());

                bw.write("\tprivate " + field.getJavaType() + " " + field.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();

//                String类型的参数
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, field.getSqlType())) {
                    String propertyName = field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;
                    bw.write("\tprivate " + field.getJavaType() + " " + propertyName + ";");
                    bw.newLine();
                    bw.newLine();
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, field.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, field.getSqlType())) {
                    bw.write("\tprivate String " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START + ";");
                    bw.newLine();
                    bw.newLine();

                    bw.write("\tprivate String " + field.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END + ";");
                    bw.newLine();
                    bw.newLine();
                }
            }

            buildGetSet(tableInfo.getFieldList(), bw);
            buildGetSet(tableInfo.getFieldExtendList(), bw);

            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            LOGGER.error("创建po失败！");
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

    private static void buildGetSet(List<FieldInfo> fieldInfoList, BufferedWriter bw) throws IOException {
        for (FieldInfo field : fieldInfoList) {
            String tempField = StringUtils.uperCaseFirstLetter(field.getPropertyName());
            // set
            bw.write("\tpublic void set" + tempField + "(" + field.getJavaType() + " " + field.getPropertyName() + ") {");
            bw.newLine();
            bw.write("\t\tthis." + field.getPropertyName() + " = " + field.getPropertyName() + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            //get
            bw.write("\tpublic " + field.getJavaType() + " get" + tempField + "() {");
            bw.newLine();
            bw.write("\t\treturn this." + field.getPropertyName() + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

        }
    }
}
