import request from '@/utils/request'

// 查询文书类基本元数据列表
export function listMetadata(query) {
  return request({
    url: '/system/metadata/list',
    method: 'get',
    params: query
  })
}

// 查询文书类基本元数据详细
export function getMetadata(id) {
  return request({
    url: '/system/metadata/' + id,
    method: 'get'
  })
}

// 新增文书类基本元数据
export function addMetadata(data) {
  return request({
    url: '/system/metadata',
    method: 'post',
    data: data
  })
}

// 修改文书类基本元数据
export function updateMetadata(data) {
  return request({
    url: '/system/metadata',
    method: 'put',
    data: data
  })
}

// 删除文书类基本元数据
export function delMetadata(id) {
  return request({
    url: '/system/metadata/' + id,
    method: 'delete'
  })
}

// 导出文书类基本元数据
export function exportMetadata(query) {
  return request({
    url: '/system/metadata/export',
    method: 'get',
    params: query
  })
}

// 导出文书类基本元数据 id
export function exportMetadataItem(ids) {
  return request({
    url: '/system/metadata/export/' + ids,
    method: 'get',
  })
}

// 导出文书类基本元数据电子文件 id
export function exportMetadataItemAndEle(ids) {
  return request({
    url: '/system/metadata/export/ele/' + ids,
    method: 'get',
  })
}

// 导出文书类基本元数据 字段
export function exportMetadataField() {
  return request({
    url: '/system/metadata/export/field',
    method: 'get',
  })
}

