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

public class BuildController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildController.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_CONTROLLER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + "Controller";
        String serviceName = tableInfo.getBeanName() + "Service";
        String lowerServiceName = StringUtils.lowerCaseFirstLetter(serviceName);

        File poFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_CONTROLLER + ";");
            bw.newLine();
            bw.newLine();

            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
//                bw.write("import java.util.Date;");
//                bw.newLine();
                bw.write("import java.util.List;");
                bw.newLine();
                bw.newLine();
                bw.write("import " + Constants.PACKAGE_VO + ".ResponseVO;");
                bw.newLine();
                bw.write("import " + Constants.PACKAGE_SERVICE + "." + serviceName + ";");
                bw.newLine();
                bw.write("import org.springframework.web.bind.annotation.RequestBody;");
                bw.newLine();
                bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
                bw.newLine();
                bw.write("import org.springframework.web.bind.annotation.RestController;");
                bw.newLine();
//                bw.write("import " + Constants.PACKAGE_UTILS + ".DateUtils;");
//                bw.newLine();
//                bw.write("import " + Constants.PACKAGE_ENUMS + ".DateTimePatternEnum;");
//                bw.newLine();
//                bw.write(Constants.BEAN_DATE_FORMAT_CLASS + ";");
//                bw.newLine();
//                bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS + ";");
//                bw.newLine();
            }

            bw.write("import " + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + ";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_QUERY + "." + tableInfo.getBeanParamName() + ";");
            bw.newLine();
            bw.newLine();

            bw.write("import javax.annotation.Resource;");
            bw.newLine();

            bw.newLine();
            BuildComment.createClassComment(bw, tableInfo.getComment() + className);
            bw.write("@RestController");
            bw.newLine();
            bw.write("@RequestMapping(\"/" + StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName()) + "\")");
            bw.newLine();
            bw.write("public class " + className + " extends ABaseController {");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate " + serviceName + " " + StringUtils.lowerCaseFirstLetter(serviceName) + ";");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "根据条件查询列表");
            bw.write("\t@RequestMapping(\"/loadDataList\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO loadDataList(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(this." + lowerServiceName + ".findListByPage(query));");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "新增");
            bw.write("\t@RequestMapping(\"/add\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO add(" + tableInfo.getBeanName() + " bean) {");
            bw.newLine();
            bw.write("\t\tthis." + lowerServiceName + ".add(bean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "批量新增");
            bw.write("\t@RequestMapping(\"/addBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO addBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tthis." + lowerServiceName + ".addBatch(listBean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldComment(bw, "批量新增或修改");
            bw.write("\t@RequestMapping(\"/addOrUpdateBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO addOrUpdateBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean) {");
            bw.newLine();
            bw.write("\t\tthis." + lowerServiceName + ".addOrUpdateBatch(listBean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
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
                bw.write("\t@RequestMapping(\"/get" + tableInfo.getBeanName() + "By" + methodName + "\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO" + " get" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(this.get" + tableInfo.getBeanName() + "By" + methodName + "(" + paramsBuilder + "));");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                // 更新
                BuildComment.createFieldComment(bw, "根据" + methodName + "更新");
                bw.write("\t@RequestMapping(\"/update" + tableInfo.getBeanName() + "By" + methodName + "\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO update" + tableInfo.getBeanName() + "By" + methodName + "(" + tableInfo.getBeanName() + " bean, " + methodParams + ") {");
                bw.newLine();
                bw.write("\t\tthis.update" + tableInfo.getBeanName() + "By" + methodName + "(bean, " + paramsBuilder + ");");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(null);");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                // 删除
                BuildComment.createFieldComment(bw, "根据" + methodName + "删除");
                bw.write("\t@RequestMapping(\"/delete" + tableInfo.getBeanName() + "By" + methodName + "\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO delete" + tableInfo.getBeanName() + "By" + methodName + "(" + methodParams + ") {");
                bw.newLine();
                bw.write("\t\tthis.delete" + tableInfo.getBeanName() + "By" + methodName + "(" + paramsBuilder + ");");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(null);");
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
