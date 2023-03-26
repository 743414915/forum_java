package com.easyjava.controller;

import java.util.List;

import com.easyjava.entity.vo.ResponseVO;
import com.easyjava.service.ProductInfoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easyjava.entity.po.ProductInfo;
import com.easyjava.entity.query.ProductInfoQuery;

import javax.annotation.Resource;

/**
 * @Description: 商品信息ProductInfoController
 * @auther: chong
 * @date: 2023/03/26
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController extends ABaseController {

	@Resource
	private ProductInfoService productInfoService;

	/**
	 * 根据条件查询列表
 	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(ProductInfoQuery query) {
		return getSuccessResponseVO(this.productInfoService.findListByPage(query));
	}

	/**
	 * 新增
 	 */
	@RequestMapping("/add")
	public ResponseVO add(ProductInfo bean) {
		this.productInfoService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
 	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<ProductInfo> listBean) {
		this.productInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增或修改
 	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<ProductInfo> listBean) {
		this.productInfoService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id查询
 	 */
	@RequestMapping("/getProductInfoById")
	public ResponseVO getProductInfoById(Integer id) {
		return getSuccessResponseVO(this.getProductInfoById(id));
	}

	/**
	 * 根据Id更新
 	 */
	@RequestMapping("/updateProductInfoById")
	public ResponseVO updateProductInfoById(ProductInfo bean, Integer id) {
		this.updateProductInfoById(bean, id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
 	 */
	@RequestMapping("/deleteProductInfoById")
	public ResponseVO deleteProductInfoById(Integer id) {
		this.deleteProductInfoById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Code查询
 	 */
	@RequestMapping("/getProductInfoByCode")
	public ResponseVO getProductInfoByCode(String code) {
		return getSuccessResponseVO(this.getProductInfoByCode(code));
	}

	/**
	 * 根据Code更新
 	 */
	@RequestMapping("/updateProductInfoByCode")
	public ResponseVO updateProductInfoByCode(ProductInfo bean, String code) {
		this.updateProductInfoByCode(bean, code);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Code删除
 	 */
	@RequestMapping("/deleteProductInfoByCode")
	public ResponseVO deleteProductInfoByCode(String code) {
		this.deleteProductInfoByCode(code);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据SkuTypeAndColorType查询
 	 */
	@RequestMapping("/getProductInfoBySkuTypeAndColorType")
	public ResponseVO getProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType) {
		return getSuccessResponseVO(this.getProductInfoBySkuTypeAndColorType(skuType, colorType));
	}

	/**
	 * 根据SkuTypeAndColorType更新
 	 */
	@RequestMapping("/updateProductInfoBySkuTypeAndColorType")
	public ResponseVO updateProductInfoBySkuTypeAndColorType(ProductInfo bean, Integer skuType, Integer colorType) {
		this.updateProductInfoBySkuTypeAndColorType(bean, skuType, colorType);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据SkuTypeAndColorType删除
 	 */
	@RequestMapping("/deleteProductInfoBySkuTypeAndColorType")
	public ResponseVO deleteProductInfoBySkuTypeAndColorType(Integer skuType, Integer colorType) {
		this.deleteProductInfoBySkuTypeAndColorType(skuType, colorType);
		return getSuccessResponseVO(null);
	}

}