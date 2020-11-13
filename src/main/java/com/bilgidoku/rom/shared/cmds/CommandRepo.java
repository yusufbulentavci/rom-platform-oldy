package com.bilgidoku.rom.shared.cmds;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.client.AddToCartCommand;
import com.bilgidoku.rom.shared.cmds.client.CartCheckoutCommand;
import com.bilgidoku.rom.shared.cmds.client.CafeCommand;
import com.bilgidoku.rom.shared.cmds.client.CommentCommand;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnAbortEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnBlurEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnChangeEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnClickEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnDblClickEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnErrorEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnFocusEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnKeyDownEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnKeyPressEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnKeyUpEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnLoadEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseDownEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseEnterEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseLeaveEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseMoveEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseOutEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseOverEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnMouseUpEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnResizeEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnScrollEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnSelectEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnSubmitEvent;
import com.bilgidoku.rom.shared.cmds.htmlevents.OnUnloadEvent;
import com.bilgidoku.rom.shared.code.Command;

public class CommandRepo {

	public final static WidgetCommand widgetCommand = new WidgetCommand();
	public final static RoutineCommand routineCommand = new RoutineCommand();
	public final static TagCommand tagCommand = new TagCommand();
	public final static TextCommand textCommand = new TextCommand();
	public final static MyErCommand myErCommand = new MyErCommand();

	private static CommandRepo one;

	public static CommandRepo one() {
		if (one == null)
			one = new CommandRepo();
		return one;
	}

	private final Map<String, Command> commands = new HashMap<String, Command>();
	private final Map<String, CommandRt> rtCommands = new HashMap<String, CommandRt>();

	private CommandRepo() {
		Command[] cmdCommands = new Command[] { textCommand, myErCommand, new LoadCommand(), new ListCommand(), new ForCommand(),
				new IfCommand(), new ElseCommand(), new ElseIfCommand(), new ChangableCommand(), new WaitCommand(),
				new ForeachCommand(), new SelectorCommand(), new SubmitCommand(), new RtDeskCommand(), new FormFieldResetCommand(),
				new ScrollCommand(), new ShowCommand(), new RedirectCommand(), new SetCommand(),
				new SetPropertyCommand(), new OnAbortEvent(), new OnBlurEvent(), new OnChangeEvent(),
				new OnClickEvent(), new OnDblClickEvent(), new OnErrorEvent(), new OnFocusEvent(),
				new OnKeyDownEvent(), new OnKeyPressEvent(), new OnKeyUpEvent(), new OnLoadEvent(),
				new OnMouseDownEvent(), new OnMouseMoveEvent(), new OnMouseOutEvent(), new OnMouseOverEvent(),
				new OnMouseUpEvent(), new OnMouseEnterEvent(), new OnMouseLeaveEvent(), new OnResizeEvent(), new OnScrollEvent(), new OnSelectEvent(),
				new OnSubmitEvent(), new OnUnloadEvent(), new FormInlineCommand(), new LogCommand(), new CommentCommand(), new AddToCartCommand(), new CartCheckoutCommand(), new CafeCommand()
				};
		
		
		for (Command it : cmdCommands) {
			commands.put(it.getCmdStr(), it);
		}
	}

	public Command get(String curTag) throws RunException {
		Command cmd = commands.get(curTag);
		if (cmd == null){
			Sistem.errln("Cmd not found:" + curTag);
			return myErCommand;
		}
		return cmd;
	}

	public Set<String> getCommandNames() {
		return commands.keySet();
	}

	public Collection<Command> getCommands() {
		return commands.values();
	}

	public CommandRt getCommandRt(String curTag) throws RunException {
		CommandRt cmd = rtCommands.get(curTag);
		if (cmd == null)
			throw new RunException("Rt Cmd not found:" + curTag);
		return cmd;
	}

	public static void addRtCommands(CommandRt[] commandRts) {
		Map<String, CommandRt> cmds = one().rtCommands;
		for (CommandRt commandRt : commandRts) {
			cmds.put(commandRt.getCmd(), commandRt);
		}
	}
	
	public void upgradeCommand(Command cmd){
		commands.put(cmd.getCmdStr(), cmd);
	}

}
