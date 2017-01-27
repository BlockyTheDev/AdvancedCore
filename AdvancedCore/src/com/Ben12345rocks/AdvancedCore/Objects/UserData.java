package com.Ben12345rocks.AdvancedCore.Objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.Thread.Thread;
import com.Ben12345rocks.AdvancedCore.Util.Files.FilesManager;
import com.Ben12345rocks.AdvancedCore.Util.Misc.ArrayUtils;
import com.Ben12345rocks.AdvancedCore.sql.Column;
import com.Ben12345rocks.AdvancedCore.sql.DataType;

public class UserData {
	private User user;

	public UserData(User user) {
		this.user = user;
	}

	public File getPlayerFile(String uuid) {
		File dFile = new File(AdvancedCoreHook.getInstance().getPlugin().getDataFolder() + File.separator + "Data",
				uuid + ".yml");

		FileConfiguration data = YamlConfiguration.loadConfiguration(dFile);
		if (!dFile.exists()) {
			FilesManager.getInstance().editFile(dFile, data);
		}
		return dFile;
	}

	public FileConfiguration getData(String uuid) {
		return Thread.getInstance().thread.getData(this, uuid);
	}

	public int getInt(String key) {
		if (!key.equals("")) {
			if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.SQLITE)) {
				List<Column> row = getSQLiteRow();
				if (row != null) {
					for (int i = 0; i < row.size(); i++) {
						if (row.get(i).getName().equals(key)) {
							try {
								return (int) row.get(i).getValue();
							} catch (ClassCastException | NullPointerException ex) {
								try {
									return Integer.parseInt((String) row.get(i).getValue());
								} catch (Exception e) {
									AdvancedCoreHook.getInstance().debug(e);
								}
							}
						}
					}
				}

			} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.MYSQL)) {
				List<Column> row = getMySqlRow();
				if (row != null) {
					for (int i = 0; i < row.size(); i++) {
						if (row.get(i).getName().equals(key)) {
							try {
								return (int) row.get(i).getValue();
							} catch (ClassCastException | NullPointerException ex) {
								try {
									return Integer.parseInt((String) row.get(i).getValue());
								} catch (Exception e) {
									AdvancedCoreHook.getInstance().debug(e);
								}
							}
						}
					}
				}
			} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.FLAT)) {
				return getData(user.getUUID()).getInt(key, 0);

			}
		}
		AdvancedCoreHook.getInstance().debug("Failed to get int from: " + key);
		return 0;
	}

	public List<Column> getSQLiteRow() {
		return AdvancedCoreHook.getInstance().getSQLiteUserTable()
				.getExact(new Column("uuid", user.getUUID(), DataType.STRING));
	}

	public List<Column> getMySqlRow() {
		return AdvancedCoreHook.getInstance().getMysql().getExact(new Column("uuid", user.getUUID(), DataType.STRING));
	}

	public String getString(String key) {
		if (!key.equals("")) {
			if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.SQLITE)) {
				List<Column> row = getSQLiteRow();
				if (row != null) {
					for (int i = 0; i < row.size(); i++) {
						if (row.get(i).getName().equals(key) && row.get(i).getDataType().equals(DataType.STRING)) {
							String st = (String) row.get(i).getValue();
							if (st != null) {
								return st;
							}
							return "";
						}
					}
				}

			} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.MYSQL)) {
				List<Column> row = getMySqlRow();
				if (row != null) {
					for (int i = 0; i < row.size(); i++) {
						if (row.get(i).getName().equals(key) && row.get(i).getDataType().equals(DataType.STRING)) {
							String st = (String) row.get(i).getValue();
							if (st != null) {
								return st;
							}
							return "";
						}
					}
				}
			} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.FLAT)) {
				return getData(user.getUUID()).getString(key, "");
			}
		}
		AdvancedCoreHook.getInstance().debug("Failed to get string from: " + key);
		return "";
	}

	public ArrayList<String> getStringList(String key) {
		String str = getString(key);
		if (str.equals("")) {
			return new ArrayList<String>();
		}
		String[] list = str.split(",");
		return ArrayUtils.getInstance().convert(list);
	}

	public void setData(final String uuid, final String path, final Object value) {
		Thread.getInstance().thread.setData(this, uuid, path, value);
	}

	public void setInt(final String key, final int value) {
		if (key.equals("")) {
			AdvancedCoreHook.getInstance().debug("No key: " + key + " to " + value);
			return;
		}
		AdvancedCoreHook.getInstance().debug("Setting " + key + " to " + value);
		if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.SQLITE)) {
			ArrayList<Column> columns = new ArrayList<Column>();
			Column primary = new Column("uuid", user.getUUID(), DataType.STRING);
			Column column = new Column(key, value, DataType.INTEGER);
			columns.add(primary);
			columns.add(column);
			AdvancedCoreHook.getInstance().getSQLiteUserTable().update(primary, columns);
		} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.MYSQL)) {
			AdvancedCoreHook.getInstance().getMysql().update(user.getUUID(), key, value, DataType.INTEGER);
		} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.FLAT)) {

			setData(user.getUUID(), key, value);

		}

	}

	public void setString(final String key, final String value) {
		if (key.equals("")) {
			AdvancedCoreHook.getInstance().debug("No key: " + key + " to " + value);
			return;
		}
		AdvancedCoreHook.getInstance().debug("Setting " + key + " to " + value);
		if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.SQLITE)) {
			ArrayList<Column> columns = new ArrayList<Column>();
			Column primary = new Column("uuid", user.getUUID(), DataType.STRING);
			Column column = new Column(key, value, DataType.STRING);
			columns.add(primary);
			columns.add(column);
			AdvancedCoreHook.getInstance().getSQLiteUserTable().update(primary, columns);
		} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.MYSQL)) {
			AdvancedCoreHook.getInstance().getMysql().update(user.getUUID(), key, value, DataType.STRING);
		} else if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.FLAT)) {

			setData(user.getUUID(), key, value);

		}
	}

	public void setStringList(final String key, final ArrayList<String> value) {
		AdvancedCoreHook.getInstance().debug("Setting " + key + " to " + value);
		String str = "";
		for (int i = 0; i < value.size(); i++) {
			if (i != 0) {
				str += ",";
			}
			str += value.get(i);
		}
		setString(key, str);

	}
}
