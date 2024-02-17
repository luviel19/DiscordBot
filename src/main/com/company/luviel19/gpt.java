package main.com.company.luviel19;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.UnsupportedEncodingException;

public class gpt extends ListenerAdapter{
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String KeyWord = "лув";
        String message = event.getMessage().getContentRaw().replace("лув"," ").replace(","," ").toLowerCase();
        String call = event.getMessage().getContentRaw().toLowerCase().replace(","," ").toLowerCase();
String[] words = call.split(" ");
        if(words[0].equals("лув")) {
            String response = generateChatGPTResponse(message);
            String fix = response.replace("\\n","\n");
            event.getChannel().sendMessage(fix).queue();
        }
    }

    public static String generateChatGPTResponse(String userPrompt) {
        String apiURL = "https://api.openai.com/v1/chat/completions";
        String apiKey = token.getTokenGpt();
        String LLMname = "gpt-3.5-turbo";
        try {
            // Create URL object
            URL url = new URL(apiURL);
            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set the request method to POST
            connection.setRequestMethod("POST");
            // Set request headers
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            // Create the request body
            String requestBody = "{\"model\": \"" + LLMname + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + userPrompt + "\"}]}";
            // Enable input/output streams
            connection.setDoOutput(true);
            // Write the request body
            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                writer.write(requestBody);
                writer.flush();
            }
            // Read the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return getLLMResponse(response.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error interacting with the ChatGPT API: " + e.getMessage(), e);
        }
    }
    private static String getLLMResponse(String response) {
        int firstChar = response.indexOf("content") + 11;
        int lastChar = response.indexOf("\"", firstChar);
        return response.substring(firstChar, lastChar);
    }

}


