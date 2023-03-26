//import com.easyjava.RunApplication;
//import com.easyjava.entity.po.ProductInfo;
//import com.easyjava.entity.query.ProductInfoQuery;
//import com.easyjava.mappers.ProductInfoMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@SpringBootTest(classes = RunApplication.class)
//@RunWith(SpringRunner.class)
//public class MapperTest {
//
//    @Resource
//    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;
//
//    @Test
//    public void selectList() {
//        ProductInfoQuery query = new ProductInfoQuery();
////        query.setId(1);
////        query.setCreateTimeStart("2023-03-26");
//        query.setCodeFuzzy("10005");
//
//        List<ProductInfo> dataList = productInfoMapper.selectList(query);
//
//        System.out.println("***********************************");
//        for (ProductInfo productInfo : dataList) {
//            System.out.println(productInfo.toString());
//        }
//
//        Integer count = productInfoMapper.selectCount(query);
//        System.out.println(count);
//    }
//
//    @Test
//    public void insert() {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setCode("10006");
//        productInfo.setSkuType(6);
//        productInfo.setColorType(0);
//        productInfo.setCreateTime(new Date());
//        productInfo.setCreateDate(new Date());
//        this.productInfoMapper.insert(productInfo);
//        System.out.println(productInfo.getId());
//    }
//
//    @Test
//    public void insertOrUpdate() {
//        ProductInfo productInfo = new ProductInfo();
////        productInfo.setId(10);
//        productInfo.setCode("10007");
//        productInfo.setProductName("测试更新4");
//        this.productInfoMapper.insertOrUpdate(productInfo);
//        System.out.println(productInfo.getId());
//    }
//
//    @Test
//    public void insertBatch() {
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setCreateDate(new Date());
//        productInfo.setCode("1000002");
//        productInfo.setCreateTime(new Date());
//        productInfoList.add(productInfo);
//
//        productInfo = new ProductInfo();
//        productInfo.setCreateDate(new Date());
//        productInfo.setCode("1000003");
//        productInfo.setCreateTime(new Date());
//        productInfoList.add(productInfo);
//
//        this.productInfoMapper.insertBatch(productInfoList);
//        System.out.println(productInfo.getId());
//    }
//
//    @Test
//    public void insertOrUpdateBatch() {
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setCreateDate(new Date());
//        productInfo.setCode("1000002");
//        productInfo.setCreateTime(new Date());
//        productInfoList.add(productInfo);
//
//        productInfo = new ProductInfo();
//        productInfo.setCreateDate(new Date());
//        productInfo.setCode("1000003");
//        productInfo.setCreateTime(new Date());
//        productInfoList.add(productInfo);
//
//        this.productInfoMapper.insertOrUpdateBatch(productInfoList);
//        System.out.println(productInfo.getId());
//    }
//
//    @Test
//    public void selectByKey() {
//        ProductInfo productInfo = productInfoMapper.selectById(9);
//
//        ProductInfo productInfo2 = productInfoMapper.selectByCode("10005");
//
//        ProductInfo productInfo3 = productInfoMapper.selectBySkuTypeAndColorType(5, 3);
//
//        System.out.println(productInfo);
//        System.out.println(productInfo2);
//        System.out.println(productInfo3);
//    }
//
//    @Test
//    public void updateByKey() {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductName("update by id");
//        productInfoMapper.updateById(productInfo, 9);
//
//        productInfo = new ProductInfo();
//        productInfo.setProductName("update by code");
//        productInfoMapper.updateByCode(productInfo, "10004");
//
//        productInfo = new ProductInfo();
//        productInfo.setProductName("update by 3 and 2");
//        Integer count = productInfoMapper.updateBySkuTypeAndColorType(productInfo, 3, 2);
//        System.out.println(count);
//        System.out.println("********************************");
//    }
//
//    @Test
//    public void deleteByKey() {
//        productInfoMapper.deleteById(10);
//
//        productInfoMapper.deleteByCode("1000001");
//
//        productInfoMapper.deleteBySkuTypeAndColorType(4, 5);
//    }
//}
