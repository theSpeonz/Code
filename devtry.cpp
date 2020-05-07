#include <bits/stdc++.h> 
using namespace std; 
  
int main() 
{ 
    fstream file; 
    string word, content[100], filename; 
    int frank=0,i=1,k=1,j=1,x=1;
    int n,index,findex,fin,max=0,count[100],fmax=0,sum=0;
    bool flag=true;
	filename = "input.txt"; 
  
    file.open(filename.c_str()); 
  
    while (file >> word) {    
    	content[k++]=word;
	}	
  	
  	for(i=1;i<k;i++){
		if(content[i]=="Frankfurt")
			frank++;
		if(i%3==0){
			n=atoi(content[i].c_str());
			if(n>max){
				max=n;
				index=i;
			}
			if(n<100 && flag){
				findex=i;
				flag=false;
			}
		}	
	}
	
	int counter = 0; 
    for (i = 1; i < k; i++) {
    	for(j=1;j<k;j++){
			if (content[i]==content[j]) 
            	counter++; 
		}
		count[x++]=counter;
    	counter=0;
	}
	for (i = 1; i < x; i++){
		if(count[i]>fmax){
			fmax=count[i];
			fin=i;
		}
	}
	for(i=1;i<k;i++){
		if(content[fin]==content[i]){
			n=atoi(content[i+2].c_str());
			sum= sum + n;
		}
	}
	
	if(k>1){
		cout << frank << endl;
		cout << content[index-2] << " " << content[index-1] << " " << content[index] << endl;	
		if(!flag)
			cout << content[findex-2] << " " << content[findex-1] << " " << content[findex] << endl;	
		else
			cout << "There is no flight with passengers less than 100." << endl;
		cout << content[fin] << " " << sum;
  	}
  	else{
  		cout << "0" << endl;
  		cout << "The file is empty!" << endl;
		cout << "There is no flight with passengers less than 100." << endl;
		cout << "The file is empty!";
	}
    return 0; 
} 
