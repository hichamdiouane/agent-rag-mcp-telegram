package diouane.hicham.rag_mcp_telegram.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AIAgent {
    private ChatClient chatClient;

    public AIAgent(ChatClient.Builder builder,
                   ChatMemory memory, ToolCallbackProvider tools, VectorStore  vectorStore) {
        // Afficher les tools disponibles
        Arrays.stream(tools.getToolCallbacks()).forEach(toolCallback -> {
            System.out.println("----------------------");
            System.out.println(toolCallback.getToolDefinition());
            System.out.println("----------------------");
        });

        this.chatClient = builder
                .defaultSystem("""
                        You are an internal assistant specialized in providing information about the employees of Adria and the blog “Cultural Diversity at Adria: The Key to Banking Innovation and Success”.
                        
                        Instructions:
                        - Answer ONLY questions related to Adria employees or the blog.
                        - If the question is outside this context, answer only: "Je ne sais pas."
                        - Do not invent information.
                        - Keep answers clear, concise, and factual.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .defaultToolCallbacks(tools)
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }
    public String askAgent(String query) {
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }
}