
const properUrl={
    textToURL: (text: string)=>{
       text = text.toLowerCase();
       for(let char of text){
           if(char === ' '){
               let index:number = text.indexOf(char);
               let newChar:string = '-';
               text = text.substring(0,index) +newChar+text.substring(index+1);
           }
       }
       return text;
   }
}

export default properUrl