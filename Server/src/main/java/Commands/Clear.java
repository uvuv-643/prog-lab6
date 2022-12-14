package Commands;

import CommandPattern.Command;
import CommandPattern.Invoker;
import CommandPattern.Receiver;
import Services.Request;
import Services.Response;

import java.util.Optional;

public class Clear implements Command {

    private final Receiver receiver;

    public Clear(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public Response execute(Request request, Invoker invoker) {
        if (request.getArgs().length == 0) {
            return receiver.clear();
        } else {
            return new Response(false, "Command <clear> is used without arguments");
        }
    }

    @Override
    public String getHelp() {
        return "Type <clear> to clear the current collection";
    }


}
