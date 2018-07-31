import java.util.*;
class border{
	int length=1000;
	int width;
}
class sensor{
	int state=0;
}
class infiltrator{
	boolean success=false;
	int pos=500;
	boolean entered=false;
}
class clock{
	float time=-1;
}
public class Simulation {
	public static Scanner sc;
	public static void main(String args[]) {
		int iteration=5;
		sc=new Scanner(System.in);  
		border b = new border();
		clock c = new clock();
		infiltrator x = new infiltrator(); 
		System.out.println("Enter the probability");
		float prob=sc.nextFloat();
		System.out.println("Enter the width of the border");
		b.width=sc.nextInt();
		int nsensor=b.length*b.width;
		sensor[] s=new sensor[nsensor];
		for(int i=0;i<s.length;i++) {
			s[i]=new sensor();
		}
		float t=0;
		float onsensor=prob*nsensor;
		Random rng = new Random();

		for(int i=0;i<iteration;i++) {			//trials
			x.success=false;
			x.entered=false;
			x.pos=500;
			while(!x.success) {
				for(int j=0;j<b.length*b.width;j++) {
					s[j].state=0;
				}
				Set<Integer> trigger = new LinkedHashSet<Integer>();
				while(trigger.size()<onsensor) {
					int size=trigger.size(); 
					int next = rng.nextInt(nsensor-1);
					trigger.add(next);
					if(size!=trigger.size()) {
						s[next].state=1;
					}
				}
				if(!x.entered) {
					int temp=x.pos;
					while(s[temp].state==1) {
						temp=temp+1;
					}
					x.pos=temp;
					x.entered=true;
					t=t+10;
					continue;
				}
				int tp=x.pos+b.length;
				if(s[tp].state==0) {
					x.pos=tp;
				}else if(tp%b.length!=0 &&  s[tp-1].state==0) {
					x.pos=tp-1;
				}else if((tp+1)%b.length!=0 && s[tp+1].state==0) {
					x.pos=tp+1;
				}
				t=t+10;
				if(x.pos/b.length>=b.width-1) {
					x.success=true;
				}
			}
		}
		c.time=t/iteration;
		System.out.printf("Average time taken to infiltrate:%fs\n",c.time);
		
		
	}
}
