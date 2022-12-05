package invest.lixinger.index.fundamental.CN;

import com.alibaba.fastjson.JSON;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNParam_RootVO;
import invest.lixinger.index.fundamental.CN.VO.indexFundamentalCNParam_RootVO_DateRange;
import invest.lixinger.utils.getResult_NoHoliday;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//{"token":"af111e8f-e2ae-47ee-a676-82d7caeeaafd","date":"2022-04-22","stockCodes":["000016"],"indexFundamentalMetricsList":["pe_ttm.y10.median","pb.y10.median","ps_ttm.y10.median"]}
//{"date":"2022-04-27","indexFundamentalMetricsList":["\"pe_ttm.y20.median\"","\"pe_ttm.y10.median\"","\"pb.y10.median\"","\"pb.y20.median\"","\"ps_ttm.y10.median\"","\"ps_ttm.y20.median\""],"stockCodes":["000016"],"token":"af111e8f-e2ae-47ee-a676-82d7caeeaafd"}
public class getParam_indexFundamentalCN {
    public static String getSingleIndexParamJsonCN() throws IOException, ParseException {
        InputStream inputStream = getParam_indexFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);
        String token = (String) indexReqParam.get("token");
        String dateYml = (String) indexReqParam.get("enddate");
        String date = getResult_NoHoliday.getResult_NoHoliday(Integer.parseInt(dateYml.substring(0, 4)));
        List<String> stockCodes = (List<String>) indexReqParam.get("stockCodes");
        List<String> indexFundamentalMetricsList = (List<String>) indexReqParam.get("indexFundamentalMetricsList");

        indexFundamentalCNParam_RootVO paramvo = new indexFundamentalCNParam_RootVO();
        paramvo.setToken(token);
        paramvo.setDate(date);
        paramvo.setMetricsList(indexFundamentalMetricsList);
        paramvo.setStockCodes(stockCodes);
        String paramJson = JSON.toJSONString(paramvo);
        return paramJson;
    }

    // 时间范围内的单一指数
    public static String getSingleIndexParam_dateRangeJsonCN(Date startDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        InputStream inputStream = getParam_indexFundamentalCN.class.getClassLoader().getResourceAsStream("indexReqParam.yml");
        Map indexReqParam = new Yaml().load(inputStream);

        String token = (String) indexReqParam.get("token");
        String startdate = sdf.format(startDate);
        String enddateYml = (String) indexReqParam.get("enddate");
        String enddate = getResult_NoHoliday.getResult_NoHoliday(Integer.parseInt(enddateYml.substring(0, 4)));
        List<String> stockCodes = (List<String>) indexReqParam.get("stockCodes");
        List<String> indexFundamentalMetricsList = (List<String>) indexReqParam.get("indexFundamentalMetricsList");

        indexFundamentalCNParam_RootVO_DateRange paramvo = new indexFundamentalCNParam_RootVO_DateRange();
        paramvo.setToken(token);
        paramvo.setStartDate(startdate);
        paramvo.setEndDate(enddate);
        paramvo.setMetricsList(indexFundamentalMetricsList);
        paramvo.setStockCodes(stockCodes);
        return JSON.toJSONString(paramvo);
    }

    // 所有指数的请求参数
    public static String getAllIndexParamJsonCN(List<String> stockCodes) throws IOException, ParseException {
        String filePath = getParam_indexFundamentalCN.class.getClassLoader().getResource("indexReqParam.yml").getPath();
        Map indexReqParam = new Yaml().load(new FileInputStream(filePath));
        List<String> indexFundamentalMetricsList = (List<String>) indexReqParam.get("indexFundamentalMetricsList");
        String token = (String) indexReqParam.get("token");
        String dateYml = (String) indexReqParam.get("enddate");
        String date = getResult_NoHoliday.getResult_NoHoliday(Integer.parseInt(dateYml.substring(0, 4)));

        indexFundamentalCNParam_RootVO paramvo = new indexFundamentalCNParam_RootVO();
        paramvo.setToken(token);
        paramvo.setDate(date);
        paramvo.setMetricsList(indexFundamentalMetricsList);
        paramvo.setStockCodes(stockCodes);
        String paramJson = JSON.toJSONString(paramvo);

        return paramJson;
    }

    public static Object getParamObjCN(String json) {
        String temp = "{\"token\":\"af111e8f-e2ae-47ee-a676-82d7caeeaafd\",\"date\":\"2022-04-27\",\"stockCodes\":[\"000016\"],\"metricsList\":[\"pe_ttm.y20.median\",\"pe_ttm.y10.median\",\"pb.y10.median\",\"pb.y20.median\",\"ps_ttm.y10.median\",\"ps_ttm.y20.median\"]}";
        return JSON.parseObject(temp, indexFundamentalCNParam_RootVO.class);
    }

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(getSingleIndexParamJsonCN());
        System.out.println("11111111111111111111111111111");
        System.out.println(getParamObjCN(new String()));
        System.out.println("22222222222222222222222222222");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "399702", "399965", "399966");
        System.out.println(getAllIndexParamJsonCN(list));
        System.out.println("333333333333333333333333333333");
        Date nearestDataInDB = new Date();
        System.out.println(getSingleIndexParam_dateRangeJsonCN(nearestDataInDB));
    }
}
