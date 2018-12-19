package face;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import cn.xsshome.taip.face.TAipFace;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Face {
	public void in(String args[]) { 
		JFrame jf = new JFrame();
		JLabel bg = new JLabel(new ImageIcon("..\\face\\image/3.jpg"));//��������
		JTextArea text=new JTextArea("       �����     "+"\n"+"\n");
		Icon icon=new ImageIcon("..\\face\\image/1.png");//���ð�ťͼƬ
		JButton open = new JButton("ѡȡ��Ƭ",icon);//������ť
		open.addActionListener(new OpenButton() {//�����ť���¼�
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("��ѡ��ͼƬ�ļ�...");//ѡ��ͼƬ
				FileNameExtensionFilter filter=new FileNameExtensionFilter("����ͼƬ��ʽ", "jpg", "jpeg", "gif", "png");
				
				chooser.setFileFilter(filter);
				int returnVal =chooser.showOpenDialog(null);
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					String imgPath = chooser.getSelectedFile().getAbsolutePath();//ͼƬ��·��
					ImageIcon icon =new ImageIcon(imgPath);
					bg.setIcon(icon);//���±���ͼƬ
					try {
						String res =deface(imgPath);//���� deface ����
						JsonParser parse =new JsonParser();
				        try {
				            JsonObject json=(JsonObject) parse.parse(res);//����jsonObject����
				            //�� json ����תΪΪ int �͵�����
				            int gen=json.get("gender").getAsInt();
				            String gender=" �Ա���";
				            if(gen<50)
				            	gender=" �Ա�Ů";
				            //�� json ����תΪΪ String �͵�����
				            String age=json.get("age").getAsString(); //����
				            String beauty=json.get("beauty").getAsString(); //��ֵ
				            int glas=json.get("glass").getAsInt();//�Ƿ�����۾���0Ϊû�У�1Ϊ��
				            String glass="";
				            if(glas==1)
				            	glass=" ��ã����۾�������";
				            
				            String expression="";//����
				            int express=json.get("expression").getAsInt();
				            if(express< 10)
				            	expression="��Ȼ����";
				            else if(express< 20)
				            	expression="������ϲ";
				            else if(express< 30)
				            	expression="��Ц��Ц";
				            else if(express< 40)
				            	expression="Ц���տ�";
				            else if(express< 50)
				            	expression="Ц���տ�";
				            else if(express< 60)
				            	expression="ϲ��ü��";
				            else if(express< 70)
				            	expression="ϲ��ü��";
				            else if(express< 80)
				            	expression="Ц�����";
				            else if(express< 90)
				            	expression="�Ļ�ŭ��";
				            else
				            	expression="һЦ���";
				            
				            int pitch=json.get("pitch").getAsInt();
				            int yaw=json.get("yaw").getAsInt();
				            int roll=json.get("roll").getAsInt();
				            StringBuffer posture=new StringBuffer(" ���ƣ�");
				            if(pitch>3)
				            	posture.append("��ͷ");
				            if(pitch<-3)
				            	posture.append("̧ͷ");
				            if(yaw!=0)
				            	posture.append("ƫͷ");
				            if(roll<-3&roll>3)
				            	posture.append("����ɱ");
				            text.setText("       �����     "+"\n"+"\n"+"\n");
				            text.setLineWrap(true);
				            text.append(gender+"\n"+"\n");
				            text.append(" ���䣺"+age+"\n"+"\n");
				            text.append(" ��ֵ��"+beauty+"\n"+"\n");
				            text.append(" ���飺"+expression+"\n"+"\n");
				            text.append(posture+"\n"+"\n");
				            text.append(glass+"\n");
				        }catch (JsonIOException e) {
				            e.printStackTrace();
				        } catch (JsonSyntaxException e) {
				            e.printStackTrace();
				        }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		text.setFont(new Font("����",Font.BOLD,24));
		jf.setTitle("��ֵ���");//���ñ���
		jf.setSize(1000,800);//���ô��ڴ�С
		jf.add(bg);//��ӱ���ͼ
		jf.add(open,"South");//��Ӱ�ť
		jf.add(text,"East");//�������
		jf.setLocation(200, 150);//���ô���λ��
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����ر��˳�����
		jf.setVisible(true);//��ʾ���
	}
	class OpenButton implements ActionListener{
		public void actionPerformed(ActionEvent event) {
		}
	}
    
    public String deface(String imgPath) throws Exception{
    	final String APP_ID = "��� APP_ID";//APP_ID
        final String APP_KEY = "��� APP_KEY";//APP_KEY
    	TAipFace aipFace = new TAipFace(APP_ID, APP_KEY);
        
        
        String result = aipFace.detect(imgPath);//������������
        
        //System.out.println(result);//������ص�����
        JsonParser parse =new JsonParser();
        try {
            JsonObject json=(JsonObject) parse.parse(result);  //����jsonObject����
            //System.out.println("resultcode:"+json.get("resultcode").getAsInt());  //�� json ����תΪΪ int �͵�����
            //System.out.println("reason:"+json.get("reason").getAsString());     //�� json ����תΪΪ String �͵�����
             
            JsonObject data=json.get("data").getAsJsonObject();
            JsonArray face_list=data.get("face_list").getAsJsonArray();
            
            JsonObject subObject=face_list.get(0).getAsJsonObject();
            String ret=subObject.toString();
            System.out.println(ret);
            return ret;
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
		return result;
		
    }
    public static void main(String args[]) throws Exception{
    	new Face().in(args);
    }
}