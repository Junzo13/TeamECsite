package com.internousdev.spring.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.spring.dao.CartInfoDAO;
import com.internousdev.spring.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware {
	private String userId;
	private int productId;
	private int productCount;
	private Map<String, Object> session;
	private CartInfoDAO cartInfoDAO = new CartInfoDAO();
	private int cartTotalPrice = 0;
	List<CartInfoDTO> productInfoListinCart = new ArrayList<CartInfoDTO>();

	public String execute() throws SQLException {
		String result = ERROR;
		String userId = null;

		// セッションタイムアウト
		if (!session.containsKey("userId") && !session.containsKey("tempUserId")) {
			return "sessionTimeout";
		}

		// ログイン状態を確認
		String tempLogined = String.valueOf(session.get("loginFlg"));
		int loginFlg = "null".equals(tempLogined) ? 0 : Integer.parseInt(tempLogined);

		// ユーザーIDを取得
		if (loginFlg == 1) {
			userId = session.get("userId").toString();
		} else {
			userId = String.valueOf(session.get("tempUserId"));
		}

		int count = 0;
		// カート情報の存在チェック
		if (cartInfoDAO.isCartInfoExistsByUserIdandProductId(userId, productId)) {
			// カート情報がある場合
			count = cartInfoDAO.addtoCart(userId, productId, productCount);
			// カート情報がない場合
		} else {
			count = cartInfoDAO.initialAddtoCart(userId, productId, productCount);
		}

		// カート情報に更新があれば、カート情報を取得する
		if (count > 0) {
			productInfoListinCart = cartInfoDAO.getProductInfoinCart(userId);
			// カート情報が存在するかどうかチェック
			if (productInfoListinCart.size() > 0) {
				// カート情報が存在した場合
				result = SUCCESS;
				for (CartInfoDTO s : productInfoListinCart) {
					setCartTotalPrice(getCartTotalPrice() + s.getPrice() * s.getProductCount());
				}
			} else {
				result = ERROR;
			}
		}

		return result;
	}

	public List<CartInfoDTO> getProductInfoListinCart() {
		return productInfoListinCart;
	}

	public void setProductInfoListinCart(List<CartInfoDTO> productInfoListinCart) {
		this.productInfoListinCart = productInfoListinCart;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getCartTotalPrice() {
		return cartTotalPrice;
	}

	public void setCartTotalPrice(int cartTotalPrice) {
		this.cartTotalPrice = cartTotalPrice;
	}

}
