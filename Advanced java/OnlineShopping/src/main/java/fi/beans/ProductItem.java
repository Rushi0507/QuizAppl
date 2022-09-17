package fi.beans;

public class ProductItem 
{
	int categoryId;
	int productId;
	float productPrice;
	int qty;
	
	public ProductItem()
	{
		
	}
	public ProductItem(int categoryId, int productID, float productPrice, int qty, int productId)
	{
		super();
		this.categoryId = categoryId;
		this.productId = productId;
		this.productPrice = productPrice;
		this.qty = qty;
		
		
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductID(int productId) {
		this.productId = productId;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}

}
