package diouane.hicham.rag_mcp_telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingAutoConfiguration.class,
		org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration.class
})

public class RagMcpTelegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagMcpTelegramApplication.class, args);
	}

}
