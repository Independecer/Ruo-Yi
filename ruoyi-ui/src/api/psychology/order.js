import request from '@/utils/request'

// 查询咨询订单列表
export function listOrder(query) {
  return request({
    url: '/psychology/order/list',
    method: 'get',
    params: query
  })
}

// 查询咨询订单详细
export function getOrder(id) {
  return request({
    url: '/psychology/order/' + id,
    method: 'get'
  })
}

// 新增咨询订单
export function addOrder(data) {
  return request({
    url: '/psychology/order',
    method: 'post',
    data: data
  })
}

// 修改咨询订单
export function updateOrder(data) {
  return request({
    url: '/psychology/order',
    method: 'put',
    data: data
  })
}

// 删除咨询订单
export function delOrder(id) {
  return request({
    url: '/psychology/order/' + id,
    method: 'delete'
  })
}
