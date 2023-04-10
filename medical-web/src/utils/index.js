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

export function imagesUrl(url){
    return `http://${location.host}/api/file-service/images/${url}`
}