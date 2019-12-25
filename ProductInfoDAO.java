package com.internousdev.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.spring.dto.ProductInfoDTO;
import com.internousdev.spring.util.DBConnector;
import com.opensymphony.xwork2.ActionSupport;

public class ProductInfoDAO extends ActionSupport {

	/**
	 * 【商品詳細のメソッド】 商品IDを引数として商品情報を取得する
	 *
	 * @param productId
	 *            (商品ID)
	 * @return 商品情報
	 */
	public ProductInfoDTO getProductInfoList(int productId) throws SQLException {
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();

		// 商品IDを条件にDBで合致する商品を検索して商品IDに格納すると同時にウォッチフラグをtrueにする
		String sql = "select * from product_info where product_id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTO;
	}

	/**
	 * 【関連商品のメソッド】 カテゴリーIDを引数として関連商品情報を取得する
	 *
	 * @param categoryId
	 *            (カテゴリーID)
	 * @param productId
	 *            (商品ID)
	 * @param limitOffset
	 *            (データを取得する初期位置)
	 * @param limitRowCount
	 *            (データを取得する件数)
	 * @return 関連商品情報
	 */
	public List<ProductInfoDTO> getRelatedProductList(int categoryId, int productId, int limitOffset, int limitRowCount)
			throws SQLException {
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "select * from product_info where category_id = ? and product_id not in(?) order by rand() limit ?,?";
		/*
		 * not in(?) = 詳細で表示されている商品が入らないようにする rand() = 表示順をランダム表示にする limit 0,3 =
		 * 0番目から3件データを取得する
		 */

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ps.setInt(3, limitOffset);
			ps.setInt(4, limitRowCount);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTOList;
	}

	/**
	 * 【キーワード検索のメソッド】キーワードの配列を引数として商品情報を取得する
	 *
	 * @param keywordsList
	 *            (キーワードの配列)
	 * @return 商品情報のList
	 */
	public List<ProductInfoDTO> getProductInfobyKeywords(String[] keywordsList) throws SQLException {
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "select * from product_info";

		if (!"".equals(keywordsList[0])) {
			for (int i = 0; i < keywordsList.length; i++) {
				if (i == 0) {
					sql += " where (product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				} else {
					sql += " or (product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				}
			}
		}

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTOList;
	}

	/**
	 * 【カテゴリーIDとキーワード検索のメソッド】カテゴリーIDとキーワードの配列を引数として商品情報を取得する
	 *
	 * @param categoryId
	 *            (カテゴリーID)
	 * @param keywordsList
	 *            (キーワードの配列)
	 * @return 商品情報のList
	 */
	public List<ProductInfoDTO> getProductInfobyCategoryIdandKeywords(int categoryId, String[] keywordsList)
			throws SQLException {
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "select * from product_info where category_id = ?";

		if (!"".equals(keywordsList[0])) {
			for (int i = 0; i < keywordsList.length; i++) {
				if (i == 0) {
					sql += " and ((product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				} else {
					sql += " or (product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				}
			}
			sql += ")";
		}

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return productInfoDTOList;
	}

}
