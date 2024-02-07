import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        String Help = event.getMessage().getContentRaw().replace(" ", "");
        if (Help.equalsIgnoreCase("?help") || Help.equalsIgnoreCase("?хелп") ) event.getChannel().sendMessage("\n"+"Что может этот бот ?" +"\n"+
                "1 : Напиши ?* Полное название рыбы*и узнай троф вес и какую прикорму лучше использовать"+"\n"+
                "Пример *  ?Амур белый  * (регистр не важен)"+"\n"+
                "Бот ответит - Прикорм:Амур,Карп/укроп,анис/комбикорм/водоросли,кортоф.кубик Троф:15кг"+"\n"+
                "2:?* Водоем * выведет список рыбы (Coming soon)");
    }
}
