package com.example.finalprojectsqliteversion;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {
    boolean isCompanyExists(String email,String Password) ;
    void addCompany(Company company) throws SQLException;
    void updateCompany(Company company) throws DataNotExists;
    void deleteCompany(int companyId) throws DataNotExists;
    ArrayList<Company> getAllCompanies() ;
    Company getOneCompany(int companyId);
}
