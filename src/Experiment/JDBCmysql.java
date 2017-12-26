package Experiment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.sql.*;

public class JDBCmysql {
	static Scanner cin;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/Stu?characterEncoding=utf8&useSSL=true";

	static final String USER = "root";
	static final String PASS = "jiang0916";
	static Connection conn = null;
	static Statement stmt = null;

	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver"); // 加载驱动器程序
		System.out.println("连接数据库...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS); // 与数据库建立链接
		stmt = conn.createStatement();

		cin = new Scanner(new BufferedInputStream(System.in));
		int op;
		String init = "        学生管理系统        \n" + "操作： \n" + "1: 创建班级\n" + "2: 查询班级信息\n" + "3: 查询个人信息\n" + "4: 增加学生\n"
				+ "5: 删除学生\n" + "6: 修改学生信息\n" + "7: 导出为excel\n" + "8: 导入excel\n" + "9: 退出";
		System.out.println(init);
		while (cin.hasNext()) {
			op = cin.nextInt();
			switch (op) {
			case 1:
				creat();
				break;
			case 2:
				queryList();
				break;
			case 3:
				query();
				break;
			case 4:
				insert();
				break;
			case 5:
				delete();
				break;
			case 6:
				modify();
				break;
			case 7:
				Export();
				break;
			case 8:
				Import();
				break;
			case 9:
				break;
			}
			if (op == 7)
				break;
			System.out.println(init);
		}
		stmt.close();
		conn.close();

	}

	private static void Import() throws SQLException, BiffException, IOException {
		System.out.println("请输入班级: ");
		String cl = cin.next();
		String sql;
		sql = "create table " + cl
				+ " (id varchar(10) not null,name varchar(20),sex varchar(10),grades float, primary key(id))";
		 int a = stmt.executeUpdate(sql);

		System.out.println("请输入文件名: ");
		String file = cin.next();
		File xlsFile = new File(file);
		// 获得工作簿对象
		Workbook workbook = Workbook.getWorkbook(xlsFile);
		// 获得所有工作表
		Sheet[] sheets = workbook.getSheets();
		// 遍历工作表
		if (sheets != null) {
			for (Sheet sheet : sheets) {
				// 获得行数
				int rows = sheet.getRows();
				// 获得列数
				int cols = sheet.getColumns();
				// 读取数据
				for (int row = 1; row < rows; row++) {
					int col = 0;
					String id = sheet.getCell(col++, row).getContents();
					String name = sheet.getCell(col++, row).getContents();
					String sex = sheet.getCell(col++, row).getContents();
					String grades = sheet.getCell(col++, row).getContents();
					sql = "insert into " + cl + " values('" + id + "','" + name + "','" + sex + "','" + grades + "')";
					a = stmt.executeUpdate(sql);
				}
			}
		}
		System.out.println("导入成功！");
		workbook.close();
		System.out.println("按q键返回主界面...");
		while (true) {
			String id = cin.next();
			if (id.equals("q"))
				break;
		}

		
	}

	private static void Export() throws IOException, SQLException, RowsExceededException, WriteException {
		System.out.println("请输入班级: ");
		String cl = cin.next();
		File xlsfile = new File(cl + ".xls");
		// 创建一个工作簿
		WritableWorkbook workbook = Workbook.createWorkbook(xlsfile);
		// 创建一个工作表
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		String sql = "select * from " + cl;
		ResultSet rs = stmt.executeQuery(sql);
		// 展开结果集数据库
		int i = 0;
		sheet.addCell(new Label(0, i, "\\"));
		sheet.addCell(new Label(1, i, "name"));
		sheet.addCell(new Label(2, i, "sex"));
		sheet.addCell(new Label(3, i, "grades"));
		i++;
		while (rs.next()) {
			// 通过字段检索
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String sex = rs.getString("sex");
			float grades = rs.getFloat("grades");
			// 输出数据
			sheet.addCell(new Label(0, i, " " + id));
			sheet.addCell(new Label(1, i, name));
			sheet.addCell(new Label(2, i, sex));
			sheet.addCell(new Label(3, i, " " + grades));
			i++;
		}
		workbook.write();
		workbook.close();
		System.out.println("按q键返回主界面...");
		while (true) {
			String id = cin.next();
			if (id.equals("q"))
				break;
		}

	}

	private static void query() throws SQLException {
		String id;
		System.out.println("请输入班级　学号:");
		String cls = cin.next();
		id = cin.next();
		String sql = "select * from " + cls + " where id = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.next()) {
			System.out.println("查询失败，" + cls + "班不存在该学号！");
		} else {
			int idd = rs.getInt("id");
			String name = rs.getString("name");
			String sex = rs.getString("sex");
			float grades = rs.getFloat("grades");

			// 输出数据
			System.out.print("ID: " + idd);
			System.out.print(", 姓名: " + name);
			System.out.print(", 性别: " + sex);
			System.out.print(", 分数: " + grades);
			System.out.print("\n");
		}
		rs.close();
		System.out.println("按q键返回主界面...");
		while (true) {
			id = cin.next();
			if (id.equals("q"))
				break;
		}

	}

	private static void modify() throws SQLException {
		String id;
		System.out.println("请输入班级　学号:");
		String cls = cin.next();
		id = cin.next();
		String sql = "select * from " + cls + " where id = " + id;
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.next()) {
			System.out.println(cls + "班不存在该学号！");
			System.out.println("按q键返回主界面...");
			while (true) {
				id = cin.next();
				if (id.equals("q"))
					break;
			}
		} else {
			int idd = rs.getInt("id");
			String name = rs.getString("name");
			String sex = rs.getString("sex");
			float grades = rs.getFloat("grades");

			// 输出数据
			System.out.print("ID: " + idd);
			System.out.print(", 姓名: " + name);
			System.out.print(", 性别: " + sex);
			System.out.print(", 分数: " + grades);
			System.out.print("\n");
			System.out.println("请输入分数: ");
			grades = cin.nextFloat();
			sql = "update Junior set grades = " + grades + "where id = " + idd;
			int a = stmt.executeUpdate(sql);
			if (a != -1)
				System.out.println("Succeed!");
			System.out.println("按q键返回主界面...");
			while (true) {
				id = cin.next();
				if (id.equals("q"))
					break;
			}

		}
		rs.close();
	}

	private static void delete() throws SQLException {
		String id;
		System.out.println("请输入班级　学号:");
		String cls = cin.next();
		id = cin.next();
		String sql = "delete from " + cls + " where id = " + id;
		int a = stmt.executeUpdate(sql);
		if (a != -1)
			System.out.println("Succeed!");
		System.out.println("按q键返回主界面...");
		while (true) {
			id = cin.next();
			if (id.equals("q"))
				break;
		}
	}

	private static void insert() throws SQLException {
		System.out.println("请输入班级 学号 姓名 性别 分数:");
		String cls = cin.next();
		String id = cin.next();
		String name = cin.next();
		String sex = cin.next();
		float grades = cin.nextFloat();
		String sql = "insert into " + cls + " values('" + id + "','" + name + "','" + sex + "','" + grades + "')";
		int a = stmt.executeUpdate(sql);
		if (a != -1)
			System.out.println("Succeed!");
		System.out.println("按q键返回主界面...");
		while (true) {
			id = cin.next();
			if (id.equals("q"))
				break;
		}

	}

	private static void queryList() throws SQLException {
		String cls;
		System.out.println("请输入班级:");
		cls = cin.next();
		String sql = "select * from " + cls;
		ResultSet rs = stmt.executeQuery(sql);
		// 展开结果集数据库
		while (rs.next()) {
			// 通过字段检索
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String sex = rs.getString("sex");
			float grades = rs.getFloat("grades");
			// 输出数据
			System.out.print("ID: " + id);
			System.out.print(", 姓名: " + name);
			System.out.print(", 性别: " + sex);
			System.out.print(", 分数: " + grades);
			System.out.print("\n");
		}
		rs.close();
		System.out.println("按q键返回主界面...");
		while (true) {
			cls = cin.next();
			if (cls.equals("q"))
				break;
		}

	}

	private static void creat() throws SQLException {
		System.out.println("请输入班级名称:\n");
		String tb = new String();
		tb = cin.next();
		String sql;
		sql = "create table " + tb
				+ " (id varchar(10) not null,name varchar(20),sex varchar(10),grades float, primary key(id))";
		int a = stmt.executeUpdate(sql);
		if (a != -1)
			System.out.println("Succeed!");
		System.out.println("按q键返回主界面...");
		while (true) {
			tb = cin.next();
			if (tb.equals("q"))
				break;
		}

	}
}
