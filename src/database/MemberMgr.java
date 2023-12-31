package database;

import beans.MemberBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

public class MemberMgr {
	
	DBConnectionMgr pool;
	public MemberMgr() {
		pool = DBConnectionMgr.getInstance();
		
	}
	public MemberBean logincheck(String id, String pw) {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = null;
		MemberBean bean = new MemberBean();
		try {
			con = pool.getConnection();
			sql = "SELECT MEMBER_ID, MEMBER_PW FROM MEMBER WHERE MEMBER_ID=? AND MEMBER_PW =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setMEMBER_ID(rs.getString(1));
				bean.setMEMBER_PW(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}

	public MemberBean joinCheck(String id, String pw, String name, String pn, String ad, String jsonum, String pay) {
		// �쉶�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 db�뜝�룞�삕 insert
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		MemberBean bean = new MemberBean();
		try {
			con = pool.getConnection();
			sql = "INSERT INTO MEMBER(MEMBER_ID, MEMBER_PW, MEMBER_NAME, TEL_NUMBER, " + 
			"ADDRESS, SOCIAL_NUMBER, PAY_PW, REG_DATETIME, MEMBER_STATUS) VALUES(?, ?, ?, ?, ?, ?, ?, NOW(), '활성')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, pn);
			pstmt.setString(5, ad);
			pstmt.setString(6, jsonum);
			pstmt.setString(7, pay);
			int rowslnserted = pstmt.executeUpdate();

			if(rowslnserted>0) {
				bean.setMEMBER_ID(id);
				bean.setMEMBER_PW(pw);;
				bean.setMEMBER_Name(name);;
				bean.setTEL_Num(pn);
				bean.setADDR(ad);
				bean.setSOCIAL_NUMBER(jsonum);
				bean.setHashedPassword(pay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return bean;
	}
	
	public boolean idDuplicationCheck(String id) { // 아이디가 중복인지 체크하는 메소드
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}
	
	public MemberBean getFindId(String name, String pn) { // 아이디 찾기 메소드
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberBean bean = new MemberBean();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM MEMBER WHERE MEMBER_NAME = ? AND TEL_NUMBER = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pn);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setMEMBER_ID(rs.getString("MEMBER_ID"));
				bean.setMEMBER_Name(rs.getString("MEMBER_NAME"));
				bean.setTEL_Num(rs.getString("TEL_NUMBER"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	
	public MemberBean getFindPw(String name, String id) { // 비밀번호 찾기 메소드
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberBean bean = new MemberBean();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM MEMBER WHERE MEMBER_NAME = ? AND MEMBER_ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bean.setMEMBER_PW(rs.getString("MEMBER_PW"));
				bean.setMEMBER_Name(rs.getString("MEMBER_NAME"));
				bean.setMEMBER_ID(rs.getString("MEMBER_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bean;
	}
	
//	public static void main(String[] args) {
//		MemberMgr membermgr = new MemberMgr();
//		MemberBean memberbean = new MemberBean();
//		memberbean.setMEMBER_ID("test1");
//		memberbean.setMEMBER_PW("1234");
//		System.out.println(membermgr.logincheck(memberbean).getMEMBER_ID()+" "+
//				memberbean.getMEMBER_PW());
//		System.out.println(memberbean.getMEMBER_ID());
//		System.out.println(memberbean.getMEMBER_PW());
//	}
	public MemberBean getMemberByID(String memberID) {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    MemberBean member = new MemberBean();
	    try {
	        con = pool.getConnection();
	        String sql = "SELECT MEMBER_NAME FROM MEMBER WHERE MEMBER_ID = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, memberID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            member.setMEMBER_Name(rs.getString("Member_Name"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources here
	    }

	    return member;
	}
	
	public MemberBean getMeberInfo(MemberBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM MEMBER WHERE MEMBER_ID =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getMEMBER_ID());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bean.setMEMBER_ID(rs.getString(1));
				bean.setMEMBER_PW(rs.getString(2));
				bean.setMEMBER_Name(rs.getString(3));
				bean.setTEL_Num(rs.getString(4));
				bean.setADDR(rs.getString(5));
				bean.setSOCIAL_NUMBER(rs.getString(6));
				bean.setPAYPW(rs.getString(7));
				bean.setREG_DATE(rs.getTimestamp(8));
				bean.setMEMBER_STATUS(rs.getString(9));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return bean;
	}
	
	public void setLoginTimestamp(MemberBean bean) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			con = pool.getConnection();
			sql = "UPDATE MEMBER_ADV m SET m.LAST_LOGIN_TIME = NOW() WHERE m.MEMBER_ID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getMEMBER_ID());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
	}
//	public boolean checkMemberStatus(MemberBean bean){
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql = null;
//		boolean flag = false;
//		try {
//			con = pool.getConnection();
//			sql = "SELECT FROM MEMBER";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, bean.getMEMBER_ID());
//			rs = pstmt.executeQuery();
//			
//			if (rs.next()) {
//				bean.setMEMBER_ID(rs.getString(1));
//				bean.setMEMBER_PW(rs.getString(2));
//				bean.setMEMBER_Name(rs.getString(3));
//				bean.setTEL_Num(rs.getString(4));
//				bean.setADDR(rs.getString(5));
//				bean.setSOCIAL_NUMBER(rs.getString(6));
//				bean.setPAYPW(rs.getString(7));
//				bean.setREG_DATE(rs.getTimestamp(8));
//				bean.setMEMBER_STATUS(rs.getString(9));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			pool.freeConnection(con, pstmt, rs);
//		}
//		return flag;
//	}
}