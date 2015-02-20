/**   
 * @Title: GoodsListInfo.java 
 * @Package cn.com.zhoufu.mouth.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 王小杰   
 * @date 2014-2-19 下午1:45:06
 * @version V1.0   
 */

package cn.com.zhoufu.mouth.model;

public class GoodsListInfo {

	private String goods_id;// 商品ID

	private String goods_name;// 商品名称

	private String goods_sn;// 商品唯一货号

	private String goods_number;// 商品购买数量

	private String market_price;// 商品市场销售价格

	private String goods_price;// 商品本店销售价格

	private String goods_attr;// 商品规格（例如200ml）

	private String goods_txm;

	public String getGoods_txm() {
		return goods_txm;
	}

	public void setGoods_txm(String pre_barcode) {
		this.goods_txm = pre_barcode;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public String getGoods_number() {
		return goods_number;
	}

	public void setGoods_number(String goods_number) {
		this.goods_number = goods_number;
	}

	public String getMarket_price() {
		return market_price;
	}

	public void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getGoods_attr() {
		return goods_attr;
	}

	public void setGoods_attr(String goods_attr) {
		this.goods_attr = goods_attr;
	}

	@Override
	public String toString() {
		StringBuilder json = new StringBuilder();
		json.append("{\"goods_id\":\"" + goods_id + "\",");
		json.append("\"goods_name\":\"" + goods_name + "\",");
		json.append("\"goods_sn\":\"" + goods_sn + "\",");
		json.append("\"goods_number\":\"" + goods_number + "\",");
		json.append("\"market_price\":\"" + market_price + "\",");
		json.append("\"goods_price\":\"" + goods_price + "\",");
		json.append("\"goods_txm\":\"" + goods_txm + "\",");
		json.append("\"goods_attr\":\"" + goods_attr + "\"}");
		return json.toString();
	}
}