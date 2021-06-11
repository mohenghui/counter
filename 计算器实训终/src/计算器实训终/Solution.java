package 计算器实训终;
import java.math.BigDecimal;

public class Solution {
	@SuppressWarnings("null")
	public double calculate(String s) {
		int j = 0;
		char str[] = s.toCharArray();
		char str1[] = new char[str.length];
		for (int i = 0; i < str.length;) {
			while (i < str.length && str[i] == ' ')
				i++;
			while (i < str.length && str[i] != ' ')
				str1[j++] = str[i++];
			while (i < str.length && str[i] == ' ')
				i++;
		}
		int st = 0;
		String ans = String.valueOf(str1) + ' ';
		
		return new BigDecimal(helper(ans, st)).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	int end = 0;

	public double helper(String s, int i) {
		double num = 0.0, prev = 0.0, sum = 0;
		int choose = 0;
		int chooseop=0;
		String target1="";
		String target2="";
		String target="";
		int flag=0;//记录是否target
		char prevOp = '+';
		double indexcount=1.0;
		while (i < s.length()) {
			
			target=s.substring(i,i+1);
			if(i+1<s.length())
			target1=s.substring(i,i+2);
			if(i+2<s.length())
			target2=s.substring(i,i+3);
			if((target.equals("√")||target1.equals("ln")||target2.equals("log")||target2.equals("mod"))&&flag==0)
			{
				if(target.equals("√")) {chooseop=1;i+=1;System.out.println("i"+i);}
				else if(target2.equals("log")) {chooseop=2;i+=3;}
				else if(target2.equals("mod")) {chooseop=3;i+=2;}
				else if(target1.equals("ln")) {chooseop=4;i+=2;}
				flag=1;
			}
			//数字
			if (Character.isDigit(s.charAt(i))||s.charAt(i) == '.') {
				if (s.charAt(i) == '.') {
					choose = 1;
					i++;
					indexcount=1;
				}
				if (choose == 0) {
					num = num * 10  + (s.charAt(i++)- '0');
				} 
				else {
					indexcount*=0.1;
					num = num  + (double)(s.charAt(i++)- '0')* indexcount;
					
				}
			} else if (s.charAt(i) == '(') {
				num = helper(s, ++i);
				i = end;
			} else{
				switch(chooseop)
				{
				case 1:
					num=Math.sqrt(num);
					break;
				case 2:
					num=Math.log(num)/Math.log(10);
					break;
//				case 3:
//					prev %= num;				
//					break;
				case 4:
					num=Math.log(num);
					break;
				case 0:
					break;
				}
				switch (prevOp) {
				case '+':
					sum += prev;
					prev = num;
					break;
				case '-':
					sum += prev;
					prev = -num;
					break;
				case '*':
					prev *= num;
					break;
				case '/':
					prev /= num;
					break;
				case 'd':
					prev=prev%num;
					break;
				}
				if (s.charAt(i) == ')') {
					i++;
					break;
				}
				prevOp = s.charAt(i);// 操作符
				num = 0; // 数字重置
				choose = 0;
				chooseop=0;
				target1="";
				target2="";
				target="";
				flag=0;
				i++;
			}
		}
		end = i;
		return (sum + prev);
	}
}
