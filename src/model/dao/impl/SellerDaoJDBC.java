package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;	

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;
		
		
		try {
			st = conn.prepareStatement("INSERT INTO seller\n" + 
					"		(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" + 
					"		VALUES\n" + 
					"		(?, ?, ?, ?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			
			int linhasAfetadas = st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			
		}
		
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName \n" + 
					"FROM seller INNER JOIN department \n" + 
					"ON seller.DepartmentId = department.Id \n" + 
					"WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				
				return obj;
			}
			
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		
		Seller seller = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" + 
					"FROM seller INNER JOIN department\n" + 
					"ON seller.DepartmentId = department.Id\n" + 
					"ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			
			//Conjunto está vazio
			//Vai guardar a ID e um Departamento
			Map<Integer, Department> map = new HashMap<>();
			
			
			//Enquanto o ResultSet retornar valores
			while (rs.next()) {
				
				//Na primeira iteração, não haverá departamento algum no map
				//O método get() retornará null
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//Se dep for nulo
				if (dep == null) {
					
					//Instancia com base nos valores do ResultSet
					dep = instantiateDepartment(rs);
					
					//Adiciona o dep instanciado ao map, sendo o Id desse dep o mesmo que estiver no ResultSet
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				//Passado o teste, instanciar o vendedor passando o ResultSet e o departamento
				Seller obj = instantiateSeller(rs, dep);
				
				//Adicionar vendedor à lista
				list.add(obj);
			}
		//Retornar a lista preenchida
		return list;			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" + 
					"FROM seller INNER JOIN department\n" + 
					"ON seller.DepartmentId = department.Id\n" + 
					"WHERE DepartmentId = ?\n" + 
					"ORDER BY Name");
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			
			//Conjunto está vazio
			//Vai guardar a ID e um Departamento
			Map<Integer, Department> map = new HashMap<>();
			
			
			//Enquanto o ResultSet retornar valores
			while (rs.next()) {
				
				//Na primeira iteração, não haverá departamento algum no map
				//O método get() retornará null
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//Se dep for nulo
				if (dep == null) {
					
					//Instancia com base nos valores do ResultSet
					dep = instantiateDepartment(rs);
					
					//Adiciona o dep instanciado ao map, sendo o Id desse dep o mesmo que estiver no ResultSet
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				//Passado o teste, instanciar o vendedor passando o ResultSet e o departamento
				Seller obj = instantiateSeller(rs, dep);
				
				//Adicionar vendedor à lista
				list.add(obj);
			}
		//Retornar a lista preenchida
		return list;			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
