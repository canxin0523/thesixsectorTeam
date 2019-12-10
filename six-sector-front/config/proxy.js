/**
 *  前端本地请求接口跨域配置
 */
const apiArr = [
  ''
]

let apiObj = {}

apiArr.map((item) => {
  apiObj['/' + item] = {
    target: '',
    changeOrigin: true,
    pathRewrite: {
    }
  }
  apiObj['/' + item].pathRewrite['^/' + item] = '/' + item
})

module.exports = apiObj