package study.community.cache;

import org.apache.commons.lang.StringUtils;
import study.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TagCache
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/3 14:51
 * @Version 1.0
 **/
public class TagCache {

//    自定义标签库，这里是通过java实现，没有用数据库
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagDTOS.add(tool);

        TagDTO spareTime = new TagDTO();
        spareTime.setCategoryName("充实生活");
        spareTime.setTags(Arrays.asList("movie","sing", "music", "guitar", "excise", "keep", "run", "basketball", "NBA"));
        tagDTOS.add(spareTime);

        return tagDTOS;
    }

//    通过选择的标签，筛选出没有在标签库出现的，并传到前台
    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, "，");
        List<TagDTO> tagDTOS = get();

        //map把数组的值映射出,而flatMap把两层的最里层的值映射出来
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
//        filter 方法用于通过设置的条件过滤出元素,第一个t是split数组中的每个值,第二个是条件.
        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

//    public static void main(String[] args) {
//        int i = (5 - 1) >>> 1;
//        System.out.println(i);
//    }
}