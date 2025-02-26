package fr.mrcraftcod.osuuserinfo.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Format the messages in the log file.
 *
 * @author MrCraftCod
 */
public final class LogFormatter extends Formatter
{
	private final String LINE_SEPARATOR = System.getProperty("line.separator");
	private final DateFormat dateFormat = new SimpleDateFormat("[zzz] yyyy/MM/dd HH:mm:ss:SSS");

	/**
	 * Used to format the logger.
	 *
	 * @return The new string to print in the console.
	 */
	@Override
	public String format(final LogRecord record)
	{
		this.dateFormat.setTimeZone(TimeZone.getDefault());
		final StringBuilder sb = new StringBuilder();
		sb.append("[").append(this.dateFormat.format(new Date(record.getMillis()))).append("]\t").append(record.getLevel().getLocalizedName()).append("\t").append(record.getSourceMethodName()).append(" (").append(record.getSourceClassName().replace("fr.tours.mrcraftcod.", "")).append(") -> ").append(formatMessage(record)).append(this.LINE_SEPARATOR);
		if(record.getThrown() != null)
			try
			{
				final StringWriter sw = new StringWriter();
				final PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			}
			catch(final Exception ignored)
			{
			}
		Utils.addConsoleText(sb.toString());
		return sb.toString();
	}
}