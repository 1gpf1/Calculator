package KCXJ;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public  class Cal extends JFrame implements ActionListener,ItemListener
{
	double number=0.0;      //初始的数字
	String operator="=";        //当前的运算符
	boolean bool = true;        //操作是否合法
	boolean firstDigit = true;     //输入的第一个数
	
	String []button= {"%","C","Backspace","/","7","8","9","*","4","5","6","-","1","2","3","+","+/-","0",".","="};
	JPanel panel3,panel4;
	JTextField wbk1,wbk2;
	JMenuBar jmb;
	JMenu jm;
	JRadioButton rb1,rb2;
	
public Cal() {
this.setLayout(new BorderLayout());   //整体布局	
this.setTitle("计算器");    //标题
this.setVisible(true);     //可见性
this.setResizable(false);   //是否可调整
this.setLocation(600, 600);     //窗口位于屏幕的位置
this.setDefaultCloseOperation(EXIT_ON_CLOSE); //窗口可以关闭

//菜单
jmb = new JMenuBar();
jm =new JMenu("选择");
rb1 = new JRadioButton("标准型",true);
rb2 = new JRadioButton("科学型");
ButtonGroup bg = new ButtonGroup();
this.setJMenuBar(jmb);
jmb.add(jm);
jm.add(rb1);
jm.add(rb2);
bg.add(rb1);
bg.add(rb2);
rb1.addItemListener(this);
rb2.addItemListener(this);


//文本框
panel3 = new JPanel(); 
panel3.setLayout(new BorderLayout());
wbk1 = new JTextField(50);     //文本框的大小
wbk1.setHorizontalAlignment(JTextField.RIGHT); //右对齐
wbk1.setEditable(false);      //文本框禁止编辑
panel3.add(wbk1);
this.add(panel3,BorderLayout.NORTH);


//按钮
panel4 = new JPanel();
panel4.setLayout(new GridLayout(5,4,1,1));
for(int i=0;i<button.length;i++)
{ JButton an=new JButton(button[i]); //按钮添加
an.addActionListener(this);  //为按钮添加事件监听器
panel4.add(an);
this.add(panel4,BorderLayout.CENTER);}

this.pack();   //窗口的大小
}
	
 //处理事件
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String label = e.getActionCommand();
		if (label.equals(button[2])) {
			//用户按了"Backspace"
			handleBack();
		}else if(label.equals(button[1])) {
			//用户按了"C"
			handleC();
		}else if ("0123456789.".indexOf(label) >= 0) {
			//用户按了数字键
			handleNumber(label);
		}else {
			//用户按了运算符键
			handleOperator(label);
		}
		
	}
	
	public void handleBack() {
		String text = wbk1.getText();
		if (text.length() > 0) {
	           // 退格，将文本最后一个字符去掉
	           text = text.substring(0, text.length() - 1);
	           if (text.length() == 0) {
	               // 如果文本没有了内容，则初始化计算器的各种值
	               wbk1.setText("0");
	               firstDigit=true;
	               operator = "=";
	           } else {
	               // 显示新的文本
	               wbk1.setText(text);
	           }
	       }
	}
	
	public void handleC() {
		//初始化文本框
		wbk1.setText("0");
		number=0.0;
		operator="=";
	}
	
	public void handleNumber(String key) {
		if (firstDigit) 
        {
          // 输入的第一个数字
          wbk1.setText(key);
        } 
      else if ((key.equals(".")) && (wbk1.getText().indexOf(".") < 0)) 
        {
          // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
          wbk1.setText(wbk1.getText() + ".");
        } 
      else if (!key.equals(".")) 
        {
          // 如果输入的不是小数点，则将数字附在结果文本框的后面
          wbk1.setText(wbk1.getText() + key);
        }
      // 以后输入的肯定不是第一个数字了
		firstDigit = false;
    }
	
	public void handleOperator(String key) {
		if (operator.equals("/")) 
        {
          // 除法运算
          // 如果当前结果文本框中的值等于0
          if (getNumberFromText() == 0.0)
            {
              // 操作不合法
              bool = false;
              wbk1.setText("除数不能为零");
            } 
          else 
            {
              number /= getNumberFromText();
            }
        } else if (operator.equals("+"))
            {
          // 加法运算
          number += getNumberFromText();
            } 
          else if (operator.equals("-"))
            {
          // 减法运算
        	  number -= getNumberFromText();
            } 
          else if (operator.equals("x"))
            {
          // 乘法运算
        	  number *= getNumberFromText();
            }else if (operator.equals("%")) {
          // 百分号运算，除以100
    	  number /=100;
      } else if (operator.equals("+/-")) {
          // 正数负数运算
    	  number *=(-1);
      } else if (operator.equals("=")) {
          // 赋值运算
    	  number = getNumberFromText();
      }
      if (bool) {
          // 双精度浮点数的运算
          long t1;
          double t2;
          t1 = (long)number;
          t2 = number - t1;
          if (t2 == 0) {
              wbk1.setText(String.valueOf(t1));
          } else {
              wbk1.setText(String.valueOf(number));
          }
      }
      // 运算符等于用户按的按钮
      operator = key;
      firstDigit=true;
      bool = true;
	}
	
	
	
	  public double getNumberFromText() {
	       double result = 0;
	       try {
	           result = Double.valueOf(wbk1.getText()).doubleValue();    //将文本框中的内容转换为double型
	       } catch (NumberFormatException e) {                       //数字格式化错误
	       }
	       return result;
	   }

	public static void main(String []args) {
		new Cal();
	}

	@Override    
	public void itemStateChanged(ItemEvent e)                       //选择项发生改变时事件处理
	{
		 if (e.getSource() instanceof JRadioButton)   //表示获得事件的对象源是不是单选按钮
		   {
	    if(rb2.isSelected()){           //表示你所选的
	        Cal2 a = new Cal2() ;
	        a.setVisible(true) ;
	        this.setVisible(false) ;
		
	}
		   }
}
}