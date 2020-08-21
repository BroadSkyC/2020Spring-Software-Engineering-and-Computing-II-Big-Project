let OSS = require('ali-oss');
export let client = new OSS({
    region: 'oss-cn-shanghai',
    accessKeyId: 'xxx',
    accessKeySecret: 'xxx',
    bucket: 'xxx'
});

// 上传
export async function put (filePath, file) {
    try {
        let result = await client.put(filePath, file)
        return result
    } catch (err) {
        console.log(err)
    }
}

// 删除
export async function remove (filePath) {
    try {
        let result = await client.delete(filePath)
        return result
    } catch (err) {
        console.log(err)
    }
}
