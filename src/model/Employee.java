package model;

public class Employee {
    private String empId;
    private String role;
    private String FullName;
    private String empAddress;
    private String email;
    private String tellNo;
    private String UserName;
    private String Password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee() {
    }

    public Employee(String empId, String role, String fullName, String empAddress, String email, String tellNo, String userName, String password) {
        this.empId = empId;
        this.role = role;
        FullName = fullName;
        this.empAddress = empAddress;
        this.email = email;
        this.tellNo = tellNo;
        UserName = userName;
        Password = password;
    }


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getTellNo() {
        return tellNo;
    }

    public void setTellNo(String tellNo) {
        this.tellNo = tellNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", role='" + role + '\'' +
                ", FullName='" + FullName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", email='" + email + '\'' +
                ", tellNo='" + tellNo + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
