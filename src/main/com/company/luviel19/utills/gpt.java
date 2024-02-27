//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package main.com.company.luviel19.utills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.com.company.luviel19.token;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class gpt extends ListenerAdapter {
    String apiURL = "https://api.openai.com/v1/chat/completions";
    String apiKey = token.getTokenGpt();
    String LLMname = "gpt-3.5-turbo-16k";

    public gpt() {
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        Thread thread = new Thread(() -> {
            if (!event.getAuthor().isBot()) {
                String KeyWord = "лув";
                String message = event.getMessage().getContentRaw().replace("лув", " ").replace(",", " ").replace("\n", " ");
                String call = event.getMessage().getContentRaw().toLowerCase().replace(",", " ").toLowerCase();
                String[] words = call.split(" ");
                int maxLength = 2000;
                if (words[0].equals("лув")) {
                    String response = generateChatGPTResponse(message);
                    String fix = response.replace("\\n", "\n");
                    if (fix.length() > maxLength) {
                        List<String> messages = new ArrayList();

                        for(int index = 0; index < fix.length(); index += maxLength) {
                            messages.add(fix.substring(index, Math.min(index + maxLength, fix.length())));
                        }

                        Iterator var10 = messages.iterator();

                        while(var10.hasNext()) {
                            String msg = (String)var10.next();
                            event.getChannel().sendMessage(msg).complete();
                        }
                    } else {
                        event.getChannel().sendMessage(fix).complete();
                    }
                }

            }
        });
        thread.start();
    }

    public static String generateChatGPTResponse(String userPrompt) {
        String apiURL = "https://api.openai.com/v1/chat/completions";
        String apiKey = token.getTokenGpt();
        String LLMname = "gpt-3.5-turbo-16k";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            String requestBody = "{\"model\": \"" + LLMname + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + userPrompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

            try {
                writer.write(requestBody);
                writer.flush();
            } catch (Throwable var13) {
                try {
                    writer.close();
                } catch (Throwable var12) {
                    var13.addSuppressed(var12);
                }

                throw var13;
            }

            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String var10;
            try {
                StringBuilder response = new StringBuilder();

                while(true) {
                    String line;
                    if ((line = reader.readLine()) == null) {
                        var10 = getLLMResponse(response.toString());
                        break;
                    }

                    response.append(line);
                }
            } catch (Throwable var14) {
                try {
                    reader.close();
                } catch (Throwable var11) {
                    var14.addSuppressed(var11);
                }

                throw var14;
            }

            reader.close();
            return var10;
        } catch (IOException var15) {
            throw new RuntimeException("Error interacting with the ChatGPT API: " + var15.getMessage(), var15);
        }
    }

    private static String getLLMResponse(String response) {
        int firstChar = response.indexOf("content") + 11;
        int lastChar = response.indexOf("\"      }", firstChar);
        return response.substring(firstChar, lastChar);
    }
}
