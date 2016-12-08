
import java.sql.*;


import java.util.Vector;


 


import javax.swing.table.DefaultTableModel;


 


//DB 처리


public class MemberDAO {


 


    private static final String DRIVER


        = "oracle.jdbc.driver.OracleDriver";


    private static final String URL


        = "jdbc:oracle:thin:@192.168.0.3:1521:ORCL";


    


    private static final String USER = "SCOTT"; //DB ID


    private static final String PASS = "SCOTT"; //DB 패스워드


    Member_List mList;


    


    public MemberDAO() {


    


    }


    


    public MemberDAO(Member_List mList){


        this.mList = mList;


        System.out.println("DAO=>"+mList);


    }


    


    /**DB연결 메소드*/


    public Connection getConn(){


        Connection con = null;


        


        try {


            Class.forName(DRIVER); //1. 드라이버 로딩


            con = DriverManager.getConnection(URL,USER,PASS); //2. 드라이버 연결


            


        } catch (Exception e) {


            e.printStackTrace();


        }


        


        return con;


    }


    


    


    /**한사람의 회원 정보를 얻는 메소드*/


    public MemberDTO getMemberDTO(String id){


        


        MemberDTO dto = new MemberDTO();


        


        Connection con = null;       //연결


        PreparedStatement ps = null; //명령


        ResultSet rs = null;         //결과


        


        try {


            


            con = getConn();


            String sql = "select * from tb_member where id=?";


            ps = con.prepareStatement(sql);


            ps.setString(1, id);


            


            rs = ps.executeQuery();


            


            if(rs.next()){


                dto.setId(rs.getString("id"));


                dto.setPwd(rs.getString("Pwd"));


                dto.setName(rs.getString("Name"));


                dto.setTel(rs.getString("tel"));


                dto.setAddr(rs.getString("addr"));


                dto.setBirth(rs.getString("birth"));


                dto.setJob(rs.getString("job"));


                dto.setGender(rs.getString("gender"));


                dto.setEmail(rs.getString("email"));


                dto.setIntro(rs.getString("intro"));


                


            }


        } catch (Exception e) {


            e.printStackTrace();


        }       


        


        return dto;     


    }


    


    /**멤버리스트 출력*/


    public Vector getMemberList(){


        


        Vector data = new Vector();  //Jtable에 값을 쉽게 넣는 방법 1. 2차원배열   2. Vector 에 vector추가


        


            


        Connection con = null;       //연결


        PreparedStatement ps = null; //명령


        ResultSet rs = null;         //결과


        


        try{


            


            con = getConn();


            String sql = "select * from tb_member order by name asc";


            ps = con.prepareStatement(sql);


            rs = ps.executeQuery();


            


            while(rs.next()){


                String id = rs.getString("id");


                String pwd = rs.getString("pwd");


                String name = rs.getString("name");


                String tel = rs.getString("tel");


                String addr = rs.getString("addr");


                String birth = rs.getString("birth");


                String gender = rs.getString("gender");


                String job = rs.getString("job");


                String email = rs.getString("email");


                String intro = rs.getString("intro");


                


                Vector row = new Vector();


                row.add(id);


                row.add(pwd);


                row.add(name);


                row.add(tel);


                row.add(addr);


                row.add(birth);


                row.add(job);


                row.add(gender);


                row.add(email);


                row.add(intro);


                


                data.add(row);              


            }//while


        }catch(Exception e){


            e.printStackTrace();


        }


        return data;


    }//getMemberList()


    


 


 


    /**회원 등록*/


    public boolean insertMember(MemberDTO dto){


        


        boolean ok = false;


        


        Connection con = null;       //연결


        PreparedStatement ps = null; //명령


        


        try{


            


            con = getConn();


            String sql = "insert into tb_member(" +


                        "id,pwd,name,tel,addr,birth," +


                        "job,gender,email,intro) "+


                        "values(?,?,?,?,?,?,?,?,?,?)";


            


            ps = con.prepareStatement(sql);


            ps.setString(1, dto.getId());


            ps.setString(2, dto.getPwd());


            ps.setString(3, dto.getName());


            ps.setString(4, dto.getTel());


            ps.setString(5, dto.getAddr());


            ps.setString(6, dto.getBirth());


            ps.setString(7, dto.getJob());


            ps.setString(8, dto.getGender());


            ps.setString(9, dto.getEmail());


            ps.setString(10, dto.getIntro());           


            int r = ps.executeUpdate(); //실행 -> 저장


            


            


            if(r>0){


                System.out.println("가입 성공");    


                ok=true;


            }else{


                System.out.println("가입 실패");


            }


            


                


            


        }catch(Exception e){


            e.printStackTrace();


        }


        


        return ok;


    }//insertMmeber


    


    


    /**회원정보 수정*/


    public boolean updateMember(MemberDTO vMem){


        System.out.println("dto="+vMem.toString());


        boolean ok = false;


        Connection con = null;


        PreparedStatement ps = null;


        try{


            


            con = getConn();            


            String sql = "update tb_member set name=?, tel=?, addr=?, birth=?, job=?, gender=?" +


                    ", email=?,intro=? "+ "where id=? and pwd=?";


            


            ps = con.prepareStatement(sql);


            


            ps.setString(1, vMem.getName());


            ps.setString(2, vMem.getTel());


            ps.setString(3, vMem.getAddr());


            ps.setString(4, vMem.getBirth());


            ps.setString(5, vMem.getJob());


            ps.setString(6, vMem.getGender());


            ps.setString(7, vMem.getEmail());


            ps.setString(8, vMem.getIntro());


            ps.setString(9, vMem.getId());


            ps.setString(10, vMem.getPwd());


            


            int r = ps.executeUpdate(); //실행 -> 수정


            // 1~n: 성공 , 0 : 실패


            


            if(r>0) ok = true; //수정이 성공되면 ok값을 true로 변경


            


        }catch(Exception e){


            e.printStackTrace();


        }


        


        return ok;


    }


    


    /**회원정보 삭제 :


     *tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.*/


    public boolean deleteMember(String id, String pwd){


        


        boolean ok =false ;


        Connection con =null;


        PreparedStatement ps =null;


        


        try {


            con = getConn();


            String sql = "delete from tb_member where id=? and pwd=?";


            


            ps = con.prepareStatement(sql);


            ps.setString(1, id);


            ps.setString(2, pwd);


            int r = ps.executeUpdate(); // 실행 -> 삭제


            


            if (r>0) ok=true; //삭제됨;


            


        } catch (Exception e) {


            System.out.println(e + "-> 오류발생");


        }       


        return ok;


    }


    


    


    /**DB데이터 다시 불러오기*/    


    public void userSelectAll(DefaultTableModel model) {


        


        


        Connection con = null;


        PreparedStatement ps = null;


        ResultSet rs = null;


        


        try {


            con = getConn();


            String sql = "select * from tb_member order by name asc";


            ps = con.prepareStatement(sql);


            rs = ps.executeQuery();


            


            // DefaultTableModel에 있는 데이터 지우기


            for (int i = 0; i < model.getRowCount();) {


                model.removeRow(0);


            }


 


            while (rs.next()) {


                Object data[] = { rs.getString(1), rs.getString(2),


                        rs.getString(3), rs.getString(4),


                       rs.getString(5),


                        rs.getString(6),


                        rs.getString(7),


                        rs.getString(8),


                        rs.getString(9),


                        rs.getString(10)};


 


                model.addRow(data);                


            }


 


        } catch (SQLException e) {


            System.out.println(e + "=> userSelectAll fail");


        } finally{


            


            if(rs!=null)


                try {


                    rs.close();


                } catch (SQLException e2) {


                    // TODO Auto-generated catch block


                    e2.printStackTrace();


                }


            if(ps!=null)


                try {


                    ps.close();


                } catch (SQLException e1) {


                    // TODO Auto-generated catch block


                    e1.printStackTrace();


                }


            if(con!=null)


                try {


                    con.close();


                } catch (SQLException e) {


                    // TODO Auto-generated catch block


                    e.printStackTrace();


                }


        }


    }


}
