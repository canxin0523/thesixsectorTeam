/**
 * Created by lmmz on 2019/12/10.
 */

import * as P from './api'
import axios from 'axios'

/*
  params是添加到url的请求字符串中的，用于get请求

  data是添加到请求体（body）中的， 用于post请求
*/
const request = (url, params = {}, headers) => {
  let apiConfig = {
    method: params.method,
    timeout: 10000,
    url: url,
    data: params || {},
    params: params || {},
    headers: Object.assign({
      'Content-Type': 'application/json',
      'Cache-Control': 'no-cache,no-store,max-age=0,must-revalidate'
    }, headers)
  }

  if (apiConfig.method === 'get') {
    delete apiConfig.data
  } else {
    delete apiConfig.params
  }
  delete params.method

  return axios(apiConfig);
}

/* request拦截�*/
axios.interceptors.request.use(function (config) {
  return config;
}, function (error) {
  // 错误响应数据
  return Promise.reject(error);
});

/* response拦截�*/
axios.interceptors.response.use(function (response) {
  // 正常响应处理
  // console.log(response)
  return response;
}, function (error) {
  // 错误响应数据
  return Promise.reject(error);
});

const get = (url, params, headers) => request(url, Object.assign({
  method: 'get'
}, params), headers);
const post = (url, params, headers) => request(url, Object.assign({
  method: 'POST'
}, params), headers);

export default {
  login: (params, headers) => get(P.Login, params, headers),
}
