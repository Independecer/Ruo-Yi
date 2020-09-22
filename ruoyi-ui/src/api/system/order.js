import request from '@/utils/request'

// 查询销售订单列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单详细
export function getOrder(orderId) {
  return request({
    url: '/system/order/' + orderId,
    method: 'get'
  })
}

// 新增销售订单
export function addOrder(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改销售订单
export function updateOrder(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除销售订单
export function delOrder(orderId) {
  return request({
    url: '/system/order/' + orderId,
    method: 'delete'
  })
}

// 导出销售订单
export function exportOrder(query) {
  return request({
    url: '/system/order/export',
    method: 'get',
    params: query
  })
}