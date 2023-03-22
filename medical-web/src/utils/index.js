import SnowflakeId from "snowflake-id";

/**
 * 利用雪花算法生成随机id
 * */
const snowflake = new SnowflakeId({
    mid: 42,
    offset: (2019 - 1970) * 31536000 * 1000
});

export function getUID(){
    return snowflake.generate();
}

/**
 * 获取uuid
 * */
export function getUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
    })
}

export function isTel(data){
    let expression=/^1(3[0-9]|4[57]|5[0-35-9]|7[0678]|8[0-9])\d{8}$/;
    return expression.test(data)
}

export function timeFormate(timeStr,formStr){//时间序列化，非常方便格式转化
    if(!timeStr){return ""}
    let time=new Date(timeStr),str=formStr;
    if(time !== "Invalid Date"){
        str=str.replace("yyyy",time.getFullYear());
        str=str.replace("MM",(time.getMonth()+1).toString().padStart(2,"0"));
        str=str.replace("dd",time.getDate().toString().padStart(2,"0"));
        str=str.replace("hh",time.getHours().toString().padStart(2,"0"));
        str=str.replace("mm",time.getMinutes().toString().padStart(2,"0"));
        str=str.replace("ss",time.getSeconds().toString().padStart(2,"0"));
    }
    return str;
}