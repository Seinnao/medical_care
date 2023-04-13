import SnowflakeId from "snowflake-id";
import lrz from 'lrz'//压缩库，需要通过 npm 安装下
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

//转换base64
function dataURLtoFile(dataurl, filename) {
    // 将base64转换为file文件
    // 参数dataurl base64字符串
    // 参数filename	图片名称
    let arr = dataurl.split(",");
    let mime = arr[0].match(/:(.*?);/)[1];
    let bstr = atob(arr[1]);
    let n = bstr.length;
    let u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {
        type: mime,
    });
}


export function compress(imagesFile,item){
    return new Promise((resolve,reject) =>{
        if(!item){
            item ={width:300};
        }
        lrz(imagesFile,item).then( rst =>{
            let file = dataURLtoFile(rst.base64,imagesFile.name);
            resolve(file)
        }).catch(err =>{
            reject(err)
        })
    })
}