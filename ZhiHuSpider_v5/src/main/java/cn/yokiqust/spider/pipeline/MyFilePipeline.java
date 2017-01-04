package cn.yokiqust.spider.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import org.apache.http.annotation.ThreadSafe;

import cn.yokiqust.model.Answer;
import cn.yokiqust.model.Question;
import cn.yokiqust.spider.utils.Result2Model;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

@ThreadSafe
public class MyFilePipeline extends FilePersistentBase implements Pipeline {
	public MyFilePipeline() {
		setPath("/data/webmagic/");
	}

	public MyFilePipeline(String path) {
		setPath(path);
	}

	public void process(ResultItems resultItems, Task task) {
		String path = this.path + PATH_SEPERATOR;
		try {
			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(getFile(path + "result2" + ".txt"), true), "UTF-8"));
			if (resultItems.get("question") != null) {
				Question question = Result2Model.result2Question(resultItems);
				List<Answer> answers = Result2Model.result2Answer(resultItems);
				System.out.println(question);
				printWriter.println(
						question.getName() + " 回答数: " + question.getAnswer_num() + " 关注数: " + question.getFollow_num());
				System.out.println(answers);
				for (Answer answer : answers) {
					printWriter.println(answer.getQuestion().getName() + " 作者: " + answer.getAuthor().getName() + "["
							+ answer.getAuthor().getBio() + "] 赞同数: " + answer.getCount());
				}
			}
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
