package com.zhangyue.test.nashor.document;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @desc:
 * @author: YanMeng
 * @date: 2020-01-07
 */
@Slf4j
public class DocumentUtils {

    public static void main(String[] args) {
        String html = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                "<style></style>\n" +
                "<div><span class=\"logo\"><div class=\"abc\"/></span></div>\n" +
                "<div class=\"gdtmo mo_tb b clearfix\" id=\"ttbp\">\n" +
                "    <div class=\"description\" onclick=\"mtaid.adClick(event, 'rzufwrq5b4fde01')\">根据进化论来讲，人类现在是否处于在进化的过程中？</div>\n" +
                "    <div class=\"image\">\n" +
                "        <div class=\"imgcontent\">\n" +
                "            <img src=\"http://pgdt.ugdtimg.com/gdt/0/DAApF-0AUAALQABhBdE1C8CL6FnNPK.jpg/0?ck=03722d1a1e86844c8d8acf9e1f0c873b\" \n" +
                "                 alt=\"\" \n" +
                "                 onload=\"mtaid.adElementLoad(event, 'rzufwrq5b4fde01', {}, true)\" \n" +
                "                 onerror=\"mtaid.adElementLoad(event, 'rzufwrq5b4fde01', {}, false)\" \n" +
                "                 onclick=\"mtaid.adClick(event, 'rzufwrq5b4fde01')\" />\n" +
                "            <div class=\"logo\" onclick=\"mtaid.openOfficialWebSite(event, 'rzufwrq5b4fde01')\"></div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"vda\" id = \"foo\">\n" +
                "        <span class=\"font-ad\" onclick=\"mtaid.adClick(event, 'rzufwrq5b4fde01')\">腾讯广告</span>\n" +
                "        <span class=\"title\" onclick=\"mtaid.adClick(event, 'rzufwrq5b4fde01')\">知乎</span>\n" +
                "        <span class=\"del\" onclick=\"mtaid.adClose(event, 'rzufwrq5b4fde01')\"></span>\n" +
                "    </div>\n" +
                "</div>";

        html = "<style>@charset \"utf-8\";body,div,html,img{padding:0;margin:0}.clearfix:after,.clearfix:before{content:\".\";display:block;height:0;font-size:0;visibility:hidden}.clearfix:after{clear:both}.clearfix{zoom:1}.gdtmo{background:#fff}.mo_tb{font-size:14px;line-height:1.5;box-sizing:border-box;font-family:Roboto-Regular,Arial,sans-serif}.mo_tb *{box-sizing:border-box}.image{position:relative;display:block}.t .image{margin:19px 19px 10px}.b .image{margin:0 19px 10px}.image .logo{position:absolute;width:25px;height:18px;background-size:25px 18px;bottom:0;right:0;visibility:hidden;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAkCAMAAAD8bpkFAAABR1BMVEUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADAwMHBwcKCgoNDQ0QEBAhISEkJCQuLi40NDQ5OTk9PT0/Pz9GRkZMTExRUVFaWlpdXV1eXl5gYGBkZGRmZmZpaWlsbGxvb290dHR4eHh6enqAgICCgoKDg4OJiYmMjIyNjY2Tk5Ofn5+srKyxsbG1tbW1tbW2tra4uLi6urq6urq8vLy9vb29vb2/v7/AwMDFxcXLy8vLy8vMzMzOzs7S0tLT09PV1dXX19fX19fX19fb29vd3d3d3d3e3t7f39/g4ODi4uLj4+NNBe1JAAAAbXRSTlMAAQIDBAUICgsODxATFhgZGx4fISInKi0vMDE4OTo7PT4/QUJERkdKTE1NTk9PUFRVWFlaXF1fYWJmZmdoaWtrbW5wcXN2dnd7fH2AiJCUl5iZm5ucnZ6foKGmq6yusLW1uLm6u77BwsTFxsfJfKkZ5QAAAZxJREFUOMuV09dT20AQwGHjgOmhJ6EFiE1ZEsAGU0WHmA4xIILpJnYE+Pf/P/Mgyt1JZub2bXfum5V27yKR0Khp7eoZGByRkIiHna/7lpDyESQVn3vlwwiQ2j4RKxLtGBE7EhsQsSM1CbEk9UNiSap1MXdwkxXn/vz3eFnyqV+tp88BVxaBQqYc6VTLqx6AKw4AR79CSZMmSgCcygIA/B0NIdG4+lUeQG59TGRm3wPYDyFtapMc8Lj96q+Bp+kAqVSnNQew/ZYmC8BRgLSoTQ6AnJKvAN64SXpUcgOsq4U8sGiQmDb4rOuejqmFHdd1lw3SLhbhk26t5jjOvFaYchwnpZPosHbiDphRCxfAhk4a9M5ZY3fJEjCpkw6dZAAv/Z4fA5fGv3zXyc9b4HriNd0DyOik0nzvmwD/VvyvOga4MibWaI5xzb+/+R2ZuigBFNMG+WKSM5+8vZfivLmXfkMkn8j/eXgnV2lzlVVmk92TZZHU4X+fXG4Ft99kkpcnODq7JKmNybAL81XsIh6J/LAmMbEmzfak054krMkz0V8rjiWtRCkAAAAASUVORK5CYII=)}.imgcontent{position:relative;height:0;overflow:hidden;padding-bottom:56%;-webkit-animation:all .3s ease-in 0s;transition:all .3s ease-in 0s}.vda{color:#666;padding-right:30px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;position:relative;font-size:14px;margin:0 19px 19px;line-height:1.5}.vda .icon-ad{float:left;color:#fff;background:#ccc;border-radius:2px;padding:0 4px;margin-right:5px;font-size:12px}.vda .font-ad{display:inline-block;vertical-align:middle;color:#999;background:#fff;border:1px solid #999;border-radius:2px;padding:0 4px;font-size:12px}.vda .title{vertical-align:middle}.vda .del{display:inline-block;width:13px;height:13px;position:absolute;top:50%;right:0;margin-top:-6px;background-repeat:no-repeat;background-size:13px 13px;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAMAAACelLz8AAAAM1BMVEUAAADNzc3Q0NDR0dHMzMzNzc3MzMzNzc3Nzc3Nzc3MzMzMzMzNzc3Nzc3MzMzMzMzMzMyBgp5XAAAAEHRSTlMAJCssQUhQgImT9ff4+fr7v+DyLAAAAH5JREFUKM/F0MsOgCAMRNFBQBFf/f+vlQoxWsa1rBpOaMgFAIfutKtpjVZCTpeIWAtZpJhbxZqK7B6I1qqMOhp7iLGXvMzIwzq5jUizbSHSjEqxTeUggrBcjyKRXBey1rptF966/CB+tR5/bO2+Wg9A4q1nHRJrPdfB99nKNpwXsA1BB549WwAAAABJRU5ErkJggg==);font-size:0}.description{color:#333;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;word-break:break-all;font-size:16px;display:inline-block}.t .description{margin:0 19px 10px}.b .description{margin:19px 19px 10px}img{height:100%;position:absolute;top:0;left:0;right:0;bottom:0;width:100%;-webkit-animation:all .3s ease-in 0s;transition:all .3s ease-in 0s}.icon-vedio{width:46px;height:46px;position:absolute;left:50%;top:50%;margin-left:-23px;margin-top:-23px;font-size:0;display:inline-block;cursor:pointer}.icon-play{background-position:0 0}.icon-suspend{background-position:-48px 0}.loader{position:absolute;left:50%;top:50%;margin-left:-15px;margin-top:-15px;font-size:10px;text-indent:-9999em;border-top:2px solid rgba(255,255,255,.2);border-right:2px solid rgba(255,255,255,.2);border-bottom:2px solid rgba(255,255,255,.2);border-left:2px solid #fff;-webkit-animation:load8 1.5s infinite linear;animation:load8 1.5s infinite linear}.loader,.loader:after{border-radius:50%;width:34px;height:34px}@-webkit-keyframes load8{0%{-webkit-transform:rotate(0);transform:rotate(0)}100%{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}@keyframes load8{0%{-webkit-transform:rotate(0);transform:rotate(0)}100%{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}@-webkit-keyframes wave{0%{height:2px}50%{height:15px}100%{height:8px}}@keyframes wave{0%{height:2px}50%{height:15px}100%{height:8px}}.icon-play,.icon-suspend{background-image:url(data:image/png;base64,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);background-size:93px auto}.icon-play{background-position:0 0}.icon-suspend{background-position:-47px 0}.css_stamp{content:\"20180323101734\"}</style>\n" +
                "\n" +
                "<style></style>\n" +
                "<div class=\"gdtmo mo_tb b clearfix\" id=\"ttbp\">\n" +
                "    <div class=\"description\" onclick=\"mtaid.adClick(event, 'pq4mi2a7fxrju01')\">旧家电，旧书本，废金属，金银礼品免费上门估值，电话预约</div>\n" +
                "    <div class=\"image\">\n" +
                "    <div class=\"imgcontent\">\n" +
                "        <img src=\"http://canvas.gdt.qq.com/canvas/1?viewid=%12%0C%08%95%D4%AE%E3%B1%C4%15%20%A3%AC%01%18%D9%DB%1B&ckn=94708800662037\" \n" +
                "             alt=\"\" \n" +
                "             onload=\"mtaid.adElementLoad(event, 'pq4mi2a7fxrju01', {}, true)\" \n" +
                "             onerror=\"mtaid.adElementLoad(event, 'pq4mi2a7fxrju01', {}, false)\" \n" +
                "             onclick=\"mtaid.adClick(event, 'pq4mi2a7fxrju01')\" />\n" +
                "        <div class=\"logo\" onclick=\"mtaid.openOfficialWebSite(event, 'pq4mi2a7fxrju01')\"></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "    <div class=\"vda\">\n" +
                "    <span class=\"font-ad\" onclick=\"mtaid.adClick(event, 'pq4mi2a7fxrju01')\">腾讯广告</span>\n" +
                "    <span class=\"title\" onclick=\"mtaid.adClick(event, 'pq4mi2a7fxrju01')\">北京旧物回收找我们</span>\n" +
                "    <span class=\"del\" onclick=\"mtaid.adClose(event, 'pq4mi2a7fxrju01')\"></span>\n" +
                "</div>\n" +
                "</div>";

        html = "<style>@charset \"utf-8\";body,div,html,img{padding:0;margin:0}.clearfix:after,.clearfix:before{content:\".\";display:block;height:0;font-size:0;visibility:hidden}.clearfix:after{clear:both}.clearfix{zoom:1}.gdtmo{background:#fff}.mo_tb{font-size:14px;line-height:1.5;box-sizing:border-box;font-family:Roboto-Regular,Arial,sans-serif}.mo_tb *{box-sizing:border-box}.image{position:relative;display:block}.t .image{margin:19px 19px 10px}.b .image{margin:0 19px 10px}.image .logo{position:absolute;width:25px;height:18px;background-size:25px 18px;bottom:0;right:0;visibility:hidden;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAkCAMAAAD8bpkFAAABR1BMVEUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADAwMHBwcKCgoNDQ0QEBAhISEkJCQuLi40NDQ5OTk9PT0/Pz9GRkZMTExRUVFaWlpdXV1eXl5gYGBkZGRmZmZpaWlsbGxvb290dHR4eHh6enqAgICCgoKDg4OJiYmMjIyNjY2Tk5Ofn5+srKyxsbG1tbW1tbW2tra4uLi6urq6urq8vLy9vb29vb2/v7/AwMDFxcXLy8vLy8vMzMzOzs7S0tLT09PV1dXX19fX19fX19fb29vd3d3d3d3e3t7f39/g4ODi4uLj4+NNBe1JAAAAbXRSTlMAAQIDBAUICgsODxATFhgZGx4fISInKi0vMDE4OTo7PT4/QUJERkdKTE1NTk9PUFRVWFlaXF1fYWJmZmdoaWtrbW5wcXN2dnd7fH2AiJCUl5iZm5ucnZ6foKGmq6yusLW1uLm6u77BwsTFxsfJfKkZ5QAAAZxJREFUOMuV09dT20AQwGHjgOmhJ6EFiE1ZEsAGU0WHmA4xIILpJnYE+Pf/P/Mgyt1JZub2bXfum5V27yKR0Khp7eoZGByRkIiHna/7lpDyESQVn3vlwwiQ2j4RKxLtGBE7EhsQsSM1CbEk9UNiSap1MXdwkxXn/vz3eFnyqV+tp88BVxaBQqYc6VTLqx6AKw4AR79CSZMmSgCcygIA/B0NIdG4+lUeQG59TGRm3wPYDyFtapMc8Lj96q+Bp+kAqVSnNQew/ZYmC8BRgLSoTQ6AnJKvAN64SXpUcgOsq4U8sGiQmDb4rOuejqmFHdd1lw3SLhbhk26t5jjOvFaYchwnpZPosHbiDphRCxfAhk4a9M5ZY3fJEjCpkw6dZAAv/Z4fA5fGv3zXyc9b4HriNd0DyOik0nzvmwD/VvyvOga4MibWaI5xzb+/+R2ZuigBFNMG+WKSM5+8vZfivLmXfkMkn8j/eXgnV2lzlVVmk92TZZHU4X+fXG4Ft99kkpcnODq7JKmNybAL81XsIh6J/LAmMbEmzfak054krMkz0V8rjiWtRCkAAAAASUVORK5CYII=)}.imgcontent{position:relative;height:0;overflow:hidden;padding-bottom:56%;-webkit-animation:all .3s ease-in 0s;transition:all .3s ease-in 0s}.vda{color:#666;padding-right:30px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;position:relative;font-size:14px;margin:0 19px 19px;line-height:1.5}.vda .icon-ad{float:left;color:#fff;background:#ccc;border-radius:2px;padding:0 4px;margin-right:5px;font-size:12px}.vda .font-ad{display:inline-block;vertical-align:middle;color:#999;background:#fff;border:1px solid #999;border-radius:2px;padding:0 4px;font-size:12px}.vda .title{vertical-align:middle}.vda .del{display:inline-block;width:13px;height:13px;position:absolute;top:50%;right:0;margin-top:-6px;background-repeat:no-repeat;background-size:13px 13px;background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAMAAACelLz8AAAAM1BMVEUAAADNzc3Q0NDR0dHMzMzNzc3MzMzNzc3Nzc3Nzc3MzMzMzMzNzc3Nzc3MzMzMzMzMzMyBgp5XAAAAEHRSTlMAJCssQUhQgImT9ff4+fr7v+DyLAAAAH5JREFUKM/F0MsOgCAMRNFBQBFf/f+vlQoxWsa1rBpOaMgFAIfutKtpjVZCTpeIWAtZpJhbxZqK7B6I1qqMOhp7iLGXvMzIwzq5jUizbSHSjEqxTeUggrBcjyKRXBey1rptF966/CB+tR5/bO2+Wg9A4q1nHRJrPdfB99nKNpwXsA1BB549WwAAAABJRU5ErkJggg==);font-size:0}.description{color:#333;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:2;-webkit-box-orient:vertical;word-break:break-all;font-size:16px;display:inline-block}.t .description{margin:0 19px 10px}.b .description{margin:19px 19px 10px}img{height:100%;position:absolute;top:0;left:0;right:0;bottom:0;width:100%;-webkit-animation:all .3s ease-in 0s;transition:all .3s ease-in 0s}.icon-vedio{width:46px;height:46px;position:absolute;left:50%;top:50%;margin-left:-23px;margin-top:-23px;font-size:0;display:inline-block;cursor:pointer}.icon-play{background-position:0 0}.icon-suspend{background-position:-48px 0}.loader{position:absolute;left:50%;top:50%;margin-left:-15px;margin-top:-15px;font-size:10px;text-indent:-9999em;border-top:2px solid rgba(255,255,255,.2);border-right:2px solid rgba(255,255,255,.2);border-bottom:2px solid rgba(255,255,255,.2);border-left:2px solid #fff;-webkit-animation:load8 1.5s infinite linear;animation:load8 1.5s infinite linear}.loader,.loader:after{border-radius:50%;width:34px;height:34px}@-webkit-keyframes load8{0%{-webkit-transform:rotate(0);transform:rotate(0)}100%{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}@keyframes load8{0%{-webkit-transform:rotate(0);transform:rotate(0)}100%{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}@-webkit-keyframes wave{0%{height:2px}50%{height:15px}100%{height:8px}}@keyframes wave{0%{height:2px}50%{height:15px}100%{height:8px}}.icon-play,.icon-suspend{background-image:url(data:image/png;base64,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);background-size:93px auto}.icon-play{background-position:0 0}.icon-suspend{background-position:-47px 0}.css_stamp{content:\"20180323101734\"}</style>\n" +
                "\n" +
                "<style></style>\n" +
                "<div class=\"gdtmo mo_tb b clearfix\" id=\"ttbp\">\n" +
                "    <div class=\"description\" onclick=\"mtaid.adClick(event, 'vpblhqeihcq6601')\">旧家电，旧家具，废铁废铜奢侈品，电话预约，上门拉货，免费估值</div>\n" +
                "    <div class=\"image\">\n" +
                "    <div class=\"imgcontent\">\n" +
                "        <img src=\"http://canvas.gdt.qq.com/canvas/1?viewid=%12%0C%08%B5%CD%BF%A8%B1%C4%15%20%A3%AC%01%18%E8%D6%16&ckn=94708677207733\" \n" +
                "             alt=\"\" \n" +
                "             onload=\"mtaid.adElementLoad(event, 'vpblhqeihcq6601', {}, true)\" \n" +
                "             onerror=\"mtaid.adElementLoad(event, 'vpblhqeihcq6601', {}, false)\" \n" +
                "             onclick=\"mtaid.adClick(event, 'vpblhqeihcq6601')\" />\n" +
                "        <div class=\"logo\" onclick=\"mtaid.openOfficialWebSite(event, 'vpblhqeihcq6601')\"></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "    <div class=\"vda\">\n" +
                "    <span class=\"font-ad\" onclick=\"mtaid.adClick(event, 'vpblhqeihcq6601')\">腾讯广告</span>\n" +
                "    <span class=\"title\" onclick=\"mtaid.adClick(event, 'vpblhqeihcq6601')\">百废待收，天生我�财�</span>\n" +
                "    <span class=\"del\" onclick=\"mtaid.adClose(event, 'vpblhqeihcq6601')\"></span>\n" +
                "</div>\n" +
                "</div>";

        Document document = buildDocument(gdtHtmlTrim(html));
        Set<Node> result = null;

        // 测试标签选择器
        result = select(document, "div");
        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试类选择器
//        result = select(document, ".vda");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试标签和类混合选择器
//        result = select(document, "div.foo");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试标签和属性存在混合选择器
//        result = select(document, "div[id]");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试标签和属性混合选择器
//        result = select(document, "div[id=ttbp]");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试多层级选择器
//        result = select(document, "div[id] span.title");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试多层级选择器
//        result = select(document, "div .logo");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);

        // 测试多层级选择器
//        result = select(document, "div span .abc");
//        result.stream().map(node -> node.getNodeName()+"."+((Element)node).getAttribute("class")).forEach(System.out::println);
    }

    /**
     * 广点通html裁剪
     * @param html
     * @return
     */
    public static String gdtHtmlTrim(String html){
        // 移除开头的<meta>标签
        html = html.replaceAll("<meta.*?>\n*", "");
        html = html.replaceAll("&", "&amp;");
        return "<html>" + html + "</html>";
    }

    public static Document buildDocument(String html){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(html.getBytes()));
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }

    /**
     * @param selecter
     *      1. 支持选择器 {@link DocumentUtils.Memes}
     *      2. 支持子选择, 例: div p.title 匹配div标签下class为title的p标签
     * @return
     */
    public static Set<Node> select(Document document, String selecter){

        if(document == null){
            throw new RuntimeException("error param: document cant be null!");
        }
        if(selecter == null || selecter.trim().isEmpty()){
            throw new RuntimeException("error param: selecter cant be null!");
        }

        List<String> memesList = Arrays.asList(selecter.split(" +"));
        List<Memes> memesEnumList = memesList.stream()
                                    .map(Memes::valueOfMemes)
                                    .collect(Collectors.toList());

        Set<Node> nodeSet = null;
        for(int i = 0; i < memesEnumList.size(); i++){
            String memes = memesList.get(i);
            Memes memesEnum = memesEnumList.get(i);
            if(memesEnum == null){
                throw new RuntimeException(String.format("error memes: %s", memes));
            }

            if(i == 0){
                nodeSet = select(document.getDocumentElement(), new HashSet(), memesEnum, memes);
            }else {
                if(nodeSet == null || nodeSet.isEmpty()){
                    return null;
                }
                Set<Node> tmpNodeSet = new HashSet<>();
                for(Node currNode : nodeSet){
                    tmpNodeSet.addAll(select(currNode, new HashSet(), memesEnum, memes));
                }
                nodeSet = tmpNodeSet;
            }
        }

        return nodeSet;
    }

    /**
     * 递归查找匹配memes的node
     */
    private static Set<Node> select(Node currNode, Set<Node> targetNodeList, Memes memesEnum, String memes){

        if(!currNode.hasChildNodes()){
            return targetNodeList;
        }

        NodeList nodeList = currNode.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){

            Node node = nodeList.item(i);
            // 不是element, 跳过
            if(node.getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            if(memesEnum.match((Element)node, memes)){
                targetNodeList.add(node);
            }

            // 递归
            select(node, targetNodeList, memesEnum, memes);
        }
        return targetNodeList;
    }

    /**
     * 支持的选择器
     *  1. 定义了选择器正则
     *  2. 定义了选择器匹配逻辑
     */
    private enum Memes{

        /**
         * 例: div 匹配所有div标签
         */
        Tag("^[a-z]+$"){
            @Override
            boolean match(Element node, String memes) {
                return node.getNodeName().equals(memes);
            }
        },
        /**
         * 例: .foo 匹配所有class属性为foo的标签
         */
        Class("^\\.\\w+$"){
            @Override
            boolean match(Element node, String memes) {
                String classValue = memes.replaceFirst("\\.", "");
                return node.getAttribute("class").equals(classValue);
            }
        },
        /**
         * 例: div.foo 匹配所有class属性为foo的div标签
         */
        TagClass("^[a-z]+\\.\\w+$"){
            @Override
            boolean match(Element node, String memes) {
                int pointIndex = memes.indexOf('.');

                String tag = memes.substring(0, pointIndex);
                String classValue = memes.substring(pointIndex+1);
                return node.getNodeName().equals(tag) && node.getAttribute("class").equals(classValue);
            }
        },
        /**
         * 例: a[src] 匹配所有包含src属性的a标签
         */
        TagAttr("^[a-z]+\\[[a-z]+\\]$"){
            @Override
            boolean match(Element node, String memes) {
                int leftIndex = memes.indexOf('[');
                int rightIndex = memes.indexOf(']');

                String tag = memes.substring(0, leftIndex);
                String attr = memes.substring(leftIndex+1, rightIndex);
                return node.getNodeName().equals(tag) && node.hasAttribute(attr);
            }
        },
        /**
         * 例: a[src=http://www.baidu.com] 匹配所有src属性为http://www.baidu.com的a标签
         */
        TagAttrEqual("^[a-z]+\\[[a-z]+=.+\\]$"){
            @Override
            boolean match(Element node, String memes) {
                int leftIndex = memes.indexOf('[');
                int equalIndex = memes.indexOf('=');
                int rightIndex = memes.indexOf(']');

                String tag = memes.substring(0, leftIndex);
                String attr = memes.substring(leftIndex+1, equalIndex);
                String attrValue = memes.substring(equalIndex+1, rightIndex);
                return node.getNodeName().equals(tag) && node.hasAttribute(attr) && node.getAttribute(attr).equals(attrValue);
            }
        };

        private String regex;

        Memes(String regex) {
            this.regex = regex;
        }

        abstract boolean match(Element node, String memes);

        public static Memes valueOfMemes(String memes){
            if(memes == null || memes.isEmpty()){
                return null;
            }
            for(Memes m : Memes.values()){
                if(memes.matches(m.regex)){
                    return m;
                }
            }
            return null;
        }
    }
}
