package Client;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


public class RoomPanel extends JPanel implements ActionListener{

	private int WIDTH = 750;
	private int HEIGHT = 280;
	
	JTable roomtable;
	DefaultTableModel tmodel;
	JScrollPane roomscroll;
	String data[][];
	
	JButton ready;		// 게임 준비
	JButton cancel;		// 준비 취소
	
	
	TextArea idlistarea;		// 방에 있는 IDlist
	public RoomPanel()
	{
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setBorder(new TitledBorder(new EtchedBorder(),"방목록"));
		
		String col[] = {"번호","제목","인원"};		//Table의 column

		tmodel = new DefaultTableModel(data,col)
		{
			public boolean isCellEditable(int r, int c)
			{
				return false;			//Table의 Cell을 수정 불가능 하도록 함
			}
		};
		

		roomtable = new JTable(tmodel);
		roomscroll = new JScrollPane(roomtable); 	// 테이블 설정

		roomtable.getColumnModel().getColumn(0).setPreferredWidth(20);
		roomtable.getColumnModel().getColumn(1).setPreferredWidth(150);
		roomtable.getColumnModel().getColumn(2).setPreferredWidth(40);
		// 테이블 칼럽 크기 조정
		roomtable.getTableHeader().setReorderingAllowed(false);			//테이블 컬럼의 이동을 방지
		roomtable.getTableHeader().setResizingAllowed(false);		// 테이블 컬럼의 사이즈를 고정
		roomtable.getColumnSelectionAllowed();
		roomscroll.setBounds(20, 20, 460, 245);
		this.add(roomscroll);
//		setBounds(100, 100, 500, 300);
//		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void Init()
	{

	}
	
}
