package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	/*O método createSellerDao() retorna uma instanciação de SellerDaoJDBC.
	 * Dessa forma, a implementação desse SellerDaoJDBC não é exposta.
	 * Recebe como argumento o getConnection que será passado para o conn da classe SellerDaoJDBC
	 */
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}

	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
}
