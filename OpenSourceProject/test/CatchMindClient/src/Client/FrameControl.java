package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FrameControl  extends JFrame implements  ActionListener
{


	//*************************방만들기 프레임에서 사용할 것들
	JComboBox selectNum;
	DefaultComboBoxModel nummodel;
	JLabel[] InputLabel;
	JTextField Titletext;
	
	JScrollPane roomscroll;
	String num;
	String msg;
	JButton enter;
	JButton roomMakeExit;
	//*************************방만들기 프레임에서 사용할 것들
	//*************************내정보보는 프레임
	JButton myinfoExit;
	
	//*************************내정보보는 프레임
	//*************************방정보보는 프레임
	JButton join;
	JButton loadInfo;
	JButton loadInfoExit;
	//*************************방정보보는 프레임
	
	
	
	
	
	
	public void RoomMakeFrame()
	{
		
		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		 String major[]={"3","4",
					"5","6"};

		this.setTitle("방만들기");
//		this.setSize(new Dimension(WIDTH,HEIGHT));
		
		
		
		roomMakeExit = new JButton("취소");	
		enter = new JButton("확인");	
		roomMakeExit.addActionListener(this);
		enter.addActionListener(this);
		this.setLayout(null);
		

		nummodel = new DefaultComboBoxModel();
		selectNum = new JComboBox(nummodel);		//인원수 콤보
		nummodel.addElement("3");
		nummodel.addElement("4");
		nummodel.addElement("5");
		nummodel.addElement("6");
		
		num = (String)nummodel.getElementAt(0);
		selectNum.addActionListener(this);	
//		this.add( s2 );
		InputLabel = new JLabel[2];
		InputLabel[0] = new JLabel("방 이 름  : ");
		InputLabel[1] = new JLabel("인     원  : ");
		roomMakeExit.setBounds(200,125,95,30);
		enter.setBounds(100,125,95,30);

		Titletext = new JTextField(20);
		
		InputLabel[0].setBounds(50, 50, 80, 20);
		Titletext.setBounds(200, 50, 120, 20);
		InputLabel[1].setBounds(50, 80, 80, 20);
		selectNum.setBounds(200, 80, 120, 20);
		
		this.add(InputLabel[0]);
		this.add(Titletext);
		this.add(InputLabel[1]);
		this.add(selectNum);
		this.add(roomMakeExit);
		this.add(enter);

	}
	public void removeRoomMakeFrame()
	{
		this.remove(InputLabel[0]);
		this.remove(Titletext);
		this.remove(InputLabel[1]);
		this.remove(selectNum);
		this.remove(roomMakeExit);
		this.remove(enter);
		this.setVisible(false);
	}
	
	
	 public void myInfoFrame(MyInformation myInformation){	
		 
		 this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		 

		this.setTitle("내정보");

		
		myinfoExit = new JButton("확인");	

		
		
		myinfoExit.addActionListener(this);


		this.setLayout(null);

		InputLabel = new JLabel[7];
		InputLabel[0] = new JLabel("User  I D :");
		InputLabel[1] = new JLabel("L e v e l :");
		InputLabel[2] = new JLabel("E   X   P :");
		InputLabel[3] = new JLabel("-Introduce-");
		InputLabel[4] = new JLabel(myInformation.getGameId());
		
		InputLabel[5] = new JLabel(""+myInformation.getLevel());
		InputLabel[6] = new JLabel(""+myInformation.getExp());

		myinfoExit.setBounds(150,400,95,30);
		

		InputLabel[0].setBounds(50, 50, 80, 20);
		
		InputLabel[1].setBounds(50, 80, 80, 20);

		InputLabel[2].setBounds(50, 110, 80, 20);
		InputLabel[3].setBounds(50, 140, 80, 20);
		InputLabel[4].setBounds(200, 50, 80, 20);
		InputLabel[5].setBounds(200, 80, 80, 20);
		InputLabel[6].setBounds(200, 110, 80, 20);
		
		
		
		
		this.add(InputLabel[0]);
		
		this.add(InputLabel[1]);
		this.add(myinfoExit);
		this.add(InputLabel[2]);
		this.add(InputLabel[3]);
		this.add(InputLabel[4]);
		this.add(InputLabel[5]);
		this.add(InputLabel[6]);

	}
	public void removeMyInfoframe()
	{
		this.remove(InputLabel[0]);
		
		this.remove(InputLabel[1]);
		this.remove(myinfoExit);
		this.remove(InputLabel[2]);
		this.remove(InputLabel[3]);
		this.remove(InputLabel[4]);
		this.remove(InputLabel[5]);
		this.remove(InputLabel[6]);
		this.setVisible(false);
		repaint();
	}
	
	public void RoomInformationFrame(){	//방정보
		 this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
	

		this.setTitle("방정보");

		
		this.setLayout(null);
		

        
		loadInfoExit = new JButton("취소");	
		join = new JButton("참여하기");

	}
	
	 public void loadInfo()
	 {
		 	this.setLayout(null);
		    InputLabel = new JLabel[8];
			InputLabel[0] = new JLabel("[방 번 호]");
			InputLabel[1] = new JLabel("[방 이 름]");
			InputLabel[2] = new JLabel("[방 제 한 인 원/접 속 인 원] ");
			InputLabel[3] = new JLabel("[방 참 가 자 ]");
			InputLabel[4] = new JLabel(CatchmindDriver.getFrame().getRoomNum());	
			InputLabel[5] = new JLabel(CatchmindDriver.getFrame().getTitle());
			InputLabel[6] = new JLabel(CatchmindDriver.getFrame().getPeopleCount());
			InputLabel[7] = new JLabel(CatchmindDriver.getFrame().getIdList());
			
			
			
			loadInfoExit.addActionListener(this);
			join.addActionListener(this);
			
			//exit.setBounds(200,400,95,30);
			join.setBounds(150,400,95,30);
			
//			Titletext = new JTextField(500);
			
			InputLabel[0].setBounds(50, 50, 150, 20);
			
			InputLabel[1].setBounds(50, 210,300, 20);

			InputLabel[2].setBounds(50, 130, 300, 20);
			InputLabel[3].setBounds(50, 270, 150, 20);
			InputLabel[4].setBounds(50, 80, 150, 20);
			InputLabel[5].setBounds(50, 240, 300, 20);
			InputLabel[6].setBounds(50, 160, 300, 20);
			InputLabel[7].setBounds(50, 300, 300,20);
			loadInfoExit.setBounds(200,340,95,30);
			join.setBounds(100,340,95,30);
			
			
			this.add(InputLabel[0]);
			this.add(InputLabel[1]);
			this.add(loadInfoExit);
			this.add(join);
			this.add(InputLabel[2]);
			this.add(InputLabel[3]);
			this.add(InputLabel[4]);
			this.add(InputLabel[5]);
			this.add(InputLabel[6]);
			this.add(InputLabel[7]);
			
			this.add(join);
			this.setVisible(false);
			repaint();
	 }
	 
	public void removeLoadInfo()
	{
		this.remove(InputLabel[0]);
		this.remove(InputLabel[1]);
		this.remove(loadInfoExit);
		this.remove(join);
		this.remove(InputLabel[2]);
		this.remove(InputLabel[3]);
		this.remove(InputLabel[4]);
		this.remove(InputLabel[5]);
		this.remove(InputLabel[6]);
		this.remove(InputLabel[7]);
		
		this.remove(join);
		this.setVisible(false);
		repaint();
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == selectNum)
		{  
			SoundEffect.BUTTON.Effect_play();
			num = (String)nummodel.getElementAt(selectNum.getSelectedIndex());	//인원 수 콤보박스 모델의 String을 num 저장
			System.out.println("방인원:"+num);
		}
		if(event.getSource() == loadInfoExit)
		{
			SoundEffect.BUTTON.Effect_play();
			this.removeLoadInfo();
					// 배경음악을 끔
		}
		if(event.getSource() == myinfoExit)
		{
			SoundEffect.BUTTON.Effect_play();
			this.removeMyInfoframe();
					// 배경음악을 끔
		}
		else if(event.getSource() == join)
		{
			SoundEffect.BUTTON.Effect_play();
			
			try {
				num=CatchmindDriver.getFrame().getPeopleCount().substring(0, 1);
				
				System.out.println("num"+num);
				if(Integer.parseInt(num)>Integer.parseInt(CatchmindDriver.getFrame().getPeopleCount().substring(4, 5)))	// 방 상태가 Full이 아니면 서버에 입장을 요청
				{
					msg = "[EnterRoom]" + CatchmindDriver.getFrame().getRoomNum();		//정보를 보고있는 방번호를 전달
					CatchmindDriver.getDos().writeUTF(msg);
					CatchmindDriver.getDos().writeUTF("[GameSetCancel]");
				
				}
				else
				{
					JOptionPane.showMessageDialog(join, "게임중 혹은 인원수가 full입니다",
							"ID Error", JOptionPane.ERROR_MESSAGE);
				}
							//[Enter Room] 방번호 메시지를 서버로 보냄
				this.removeLoadInfo(); //방정보 프레임 제거
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
		}
		else if(event.getSource() == enter){
			SoundEffect.BUTTON.Effect_play();
			if(Titletext.getText().equals(""))
			{
				JOptionPane.showMessageDialog(enter, "방제목을 입력하세요",
						"ID Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				
				msg= "[MakeRoom]"+ Titletext.getText() + "\t" +num ;
				
				System.out.println("방인원:"+num);
				try {
					CatchmindDriver.getDos().writeUTF(msg);
					this.removeRoomMakeFrame();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Titletext.setText("");
							
			}
			
			
			
		
			
		}
		
		
		
		
		

	}

}


