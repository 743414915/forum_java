package common.easyjava;

import common.easyjava.bean.TableInfo;
import common.easyjava.builder.*;

import common.easyjava.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RunApplication {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(RunApplication.class);

    public static void main(String[] args) {
        List<TableInfo> tableInfoList = BuildTable.getTables();

        BuildBase.execute();
        for (TableInfo tableInfo : tableInfoList) {
            BuildPo.execute(tableInfo);
            BuildQuery.execute(tableInfo);
            BuildMapper.execute(tableInfo);
        }
    }
}
