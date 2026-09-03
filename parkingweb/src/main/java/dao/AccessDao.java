package dao;

import java.util.List;

import model.AccessTable;

public interface AccessDao {
	
		public int InsertAccessLog(AccessTable accesslogtable);
		public List<AccessTable> selectAllAccess(String startDate, String endDate, String lincensePlateNumber);
		
}
