import pandas as pd
import numpy as np

# must have
# xlrd == 1.2
# to read xlsx files.

df = pd.read_excel('Mellat data (10000).xlsx', nrows=1000)
print(df.shape)
# print(df.shape)
# print(df.head())
print(df.columns)
# new_df = df[['sector', 'gender']]
# get unique values
# print(df['sector'].unique())
# print(df['gender'].unique())
# print((new_df[new_df['sector'] == 1]).shape)
# print((new_df[new_df['sector'] == 2]).shape)
# print((new_df[new_df['sector'] == 3]).shape)
# print((new_df[new_df['sector'] == 4]).shape)
# print((new_df[new_df['sector'] == 5]).shape)
print(df['label'].unique())
cols = df.columns
for col in cols:
    print(df[col].dtype)
print(len(np.unique(df['gender'])))
