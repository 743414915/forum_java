package common.easyjava.builder;

import common.easyjava.bean.Constants;
import common.easyjava.bean.FieldInfo;
import common.easyjava.bean.TableInfo;
import common.easyjava.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BuildServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildServiceImpl.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_SERVICE_IMPL);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + "ServiceImpl";
        String interfaceName = tableInfo.getBeanName() + "Service";
        String mapperName = tableInfo.getBeanName() + Constants.SUFFIX_MAPPERS;
        String lowerMapperName = StringUtils.lowerCaseFirstLetter(mapperName);

        File poFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_SERVICE_IMPL + ";");
            bw.newLine();
            bw.newLine();


            bw.write("import java.util.List;");
            bw.newLine();
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + ".SimplePage;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".PaginationResultVO;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_ENUMS + ".PageSize;;");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_MAPPERS + "." + mapperName + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_SERVICE + "." + interfaceName + ";");
            bw.newLine();
            bw.write("import org.springframework.stereotype.Service;");
            bw.newLine();

            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.newLine();

            bw.write("import javax.annotation.Resource;");
            bw.newLine();

            bw.newLine();
            BuildComment.createClassComment(bw, tableInfo.getComment() + className);
            bw.write("@Service(\"" + StringUtils.lowerCaseFirstLetter(interfaceName) + "\")");
            bw.newLine();
            bw.write("public class " + className + " implements " + interfaceName + " {");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate " + mapperName + "<" + tableInfo.getBeanName() + ", " + tableInfo.getBeanParamName() + "> " + StringUtils.lowerCaseFirstLetter(mapperName) + ";");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "根据条件查询列表");
            bw.write("\tpublic List<" + tableInfo.getBeanName() + "> findListByParam(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn this." + lowerMapperName + ".selectList(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "根据条件查询数量");
            bw.write("\tpublic Integer findCountByParam(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn this." + lowerMapperName + ".selectCount(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "分页查询");
            bw.write("\tpublic PaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\tInteger count = this.findCountByParam(query);");
            bw.newLine();
            bw.write("\t\tint pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();");
            bw.newLine();
            bw.newLine();
            bw.write("\t\tSimplePage page = new SimplePage(query.getPageNo(), count, pageSize);");
            bw.newLine();
            bw.write("\t\tquery.setSimplePage(page);");
            bw.newLine();
            bw.write("\t\tList<" + tableInfo.getBeanName() + "> list = this.findListByParam(query);");
            bw.newLine();
            bw.write("\t\tPaginationResultVO<" + tableInfo.getBeanName() + "> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);");
            bw.newLine();
            bw.write("\t\treturn result;");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "新增");
            bw.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean) {");
            bw.newLine();
            bw.write("\t\treturn this." + lowerMapperName + ".insert(bean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "批量新增");
            bw.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this." + lowerMapperName + ".insertBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "批量新增或修改");
            bw.write("\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this." + lowerMapperName + ".insertOrUpdateBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                Integer index = 0;
                StringBuilder methodName = new StringBuilder();

                StringBuilder methodParams = new StringBuilder();

                StringBuilder paramsBuilder = new StringBuilder();

                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                    }

                    methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodParams.append(", ");
                    }

                    paramsBuilder.append(fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        paramsBuilder.append(", ");
                    }
                }
                // 查询
                BuildComment.createFieldComment(bw, "根据" + methodName + "查询");
                bw.write("\tpublic " + tableInfo.getBeanName() + " get" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + lowerMapperName + ".selectBy" + methodName + "(" + paramsBuilder + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                // 更新
                BuildComment.createFieldComment(bw, "根据" + methodName + "更新");
                bw.write("\tpublic Integer update" + tableInfo.getBeanName() + "By" + methodName + "(" + tableInfo.getBeanName() + " bean, " + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + lowerMapperName + ".updateBy" + methodName + "(bean, " + paramsBuilder + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                // 删除
                BuildComment.createFieldComment(bw, "根据" + methodName + "删除");
                bw.write("\tpublic Integer delete" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn this." + lowerMapperName + ".deleteBy" + methodName + "(" + paramsBuilder + ");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }

            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            LOGGER.error("创建service失败！");
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
