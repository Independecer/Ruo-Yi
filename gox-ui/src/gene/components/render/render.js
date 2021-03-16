import {deepClone} from '@/gene/utils'
import {listDefinition} from '@/api/system/metadataRule'

const componentChild = {}
/**
 * 将./slots中的文件挂载到对象componentChild上
 * 文件名为key，对应JSON配置中的__config__.tag
 * 文件内容为value，解析JSON配置中的__slot__
 */
const slotsFiles = require.context('./slots', false, /\.js$/)
const keys = slotsFiles.keys() || []
keys.forEach(key => {
  const tag = key.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = slotsFiles(key).default
  componentChild[tag] = value
})

function vModel(dataObject, defaultValue) {
  dataObject.props.value = defaultValue

  dataObject.on.input = val => {
    this.$emit('input', val)
  }
}

function mountSlotFlies(h, confClone, children) {
  const childObjs = componentChild[confClone.__config__.tag]
  if (childObjs) {
    Object.keys(childObjs).forEach(key => {
      const childFunc = childObjs[key]
      if (confClone.__slot__ && confClone.__slot__[key]) {
        children.push(childFunc(h, confClone, key))
      }
    })
  }
}

function emitEvents(confClone) {
  ['on', 'nativeOn'].forEach(attr => {
    const eventKeyList = Object.keys(confClone[attr] || {})
    eventKeyList.forEach(key => {
      const val = confClone[attr][key]
      if (typeof val === 'string') {
        confClone[attr][key] = event => this.$emit(val, event)
      }
    })
  })
}

function buildDataObject(confClone, dataObject) {
  Object.keys(confClone).forEach(key => {
    const val = confClone[key]
    if (key === '__vModel__') {
      vModel.call(this, dataObject, confClone.__config__.defaultValue)
    } else if (dataObject[key]) {
      dataObject[key] = {...dataObject[key], ...val}
    } else {
      dataObject.attrs[key] = val
    }
  })

  // 清理属性
  clearAttrs(dataObject)
}

function clearAttrs(dataObject) {
  delete dataObject.attrs.__config__
  delete dataObject.attrs.__slot__
  delete dataObject.attrs.__methods__
}

function makeDataObject() {
  return {
    attrs: {},
    props: {},
    nativeOn: {},
    on: {},
    style: {}
  }
}

export default {
  props: {
    conf: {
      type: Object,
      required: true
    },
  },
  data() {
    return {
      tip: ''
    }
  },
  methods: {
    loadData(name) {
      let d = JSON.parse(unescape(window.sessionStorage.getItem('WS')))
      if (d) {
        let definitions = d.filter(i => {
          return i.nameCn === name
        })
        if (definitions.length === 0) {
          listDefinition(name).then(res => {
            this.tip = res.data[0].definition;
          }).catch(err => {
            console.log(err.message)
          })
        } else {
          this.tip = definitions[0].definition;
        }
      }
    },
    getArchival(){

    },
  },
  created() {
    this.loadData(this.conf.__config__.label)
  },
  render(h) {
    const dataObject = makeDataObject()
    const confClone = deepClone(this.conf)
    const children = this.$slots.default || []
    // 如果slots文件夹存在与当前tag同名的文件，则执行文件中的代码
    mountSlotFlies.call(this, h, confClone, children)
    // 将字符串类型的事件，发送为消息
    emitEvents.call(this, confClone)
    // 将json表单配置转化为vue render可以识别的 “数据对象（dataObject）”
    buildDataObject.call(this, confClone, dataObject)
    let child = h(this.conf.__config__.tag, dataObject, children);
    if (!this.tip) {
      return child
    }
    return h('el-tooltip', {attrs: {effect: "dark", content: this.tip, placement: "top"}}, [child])
  }
}
