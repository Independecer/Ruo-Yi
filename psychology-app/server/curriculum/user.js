import httprequest from "../httpRequest";
export default {
  //获取测评详情
  getProductInfo: async (id) => {
    let res = await httprequest.post("/app/home/gauge/getInfo?id=" + id);
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取详情出错",
      });
    }
  },
  getOrderList: async (gaugeStatus) => {
    let res = await httprequest.get(
      "/app/gauge/order/list?gaugeStatus=" + (gaugeStatus || "")
    );
    if (res.code == 200) {
      return res.rows;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取订单出错",
      });
    }
  },
  getOrderListNum: async () => {
    let res = await httprequest.post("/app/gauge/order/getMyReportNum");
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取数量出错",
      });
    }
  },
  changeOrderStatus:async ()=>{
    let res = await httprequest.post("/app/gauge/order/changeOrderStatus");
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取数量出错",
      });
    }
  }
};
