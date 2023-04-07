import request from '@/utils/request'

// 查询类型列表
export function listClass(query) {
  return request({
    url: '/course/class/list',
    method: 'get',
    params: query
  })
}

// 查询类型详细
export function getClass(id) {
  return request({
    url: '/course/class/' + id,
    method: 'get'
  })
}

// 新增类型
export function addClass(data) {
  return request({
    url: '/course/class',
    method: 'post',
    data: data
  })
}

// 修改类型
export function updateClass(data) {
  return request({
    url: '/course/class',
    method: 'put',
    data: data
  })
}

// 删除类型
export function delClass(id) {
  return request({
    url: '/course/class/' + id,
    method: 'delete'
  })
}
