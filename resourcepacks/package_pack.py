from datetime import datetime, timezone
import os
import shutil
import sys

def change_timestamp(file_path):
    new_time = datetime(year = 1980, month = 1, day = 1, hour = 0, minute = 0, second = 0, microsecond = 0).timestamp()
    os.utime(path = file_path, times = (new_time, new_time))


def change_timestamp_of_files(path):
    if os.path.isdir(path):
        change_timestamp(path)
        files = os.listdir(path)
        for file in files:
            change_timestamp_of_files(path + "\\" + file)
    else:
        change_timestamp(path)

def main():
    args = sys.argv

    if len(args) < 2:
        return

    base_file_name = args[1]

    change_timestamp_of_files(base_file_name)
    date = datetime.now(timezone.utc).strftime('%Y%m%d')
    zip_name = base_file_name #+ '_' + date
    shutil.make_archive(base_file_name + '/' + zip_name, 'zip', root_dir = base_file_name + '/src')

if __name__ == '__main__':
    main()
