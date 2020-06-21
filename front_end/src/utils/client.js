let OSS = require('ali-oss');
export let client = new OSS({
    region: 'oss-cn-shanghai',
    accessKeyId: 'LTAI4GDPCV3LpbpnQjCoyXNC',
    accessKeySecret: 'DXdQfTosIkscieZ1zBn1F2RX3Q0jnT',
    bucket: 'seec67'
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