import argparse
from pathlib import Path

import pandas as pd
from joblib import dump
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report


def main():
    parser = argparse.ArgumentParser(description="Train a simple resume skill classifier (NLP basics).")
    parser.add_argument("--data", default=str(Path(__file__).resolve().parents[1] / "data" / "training_data.csv"))
    parser.add_argument("--model_out", default=str(Path(__file__).resolve().parents[1] / "model.joblib"))
    parser.add_argument("--test_size", type=float, default=0.25)
    parser.add_argument("--random_state", type=int, default=42)
    args = parser.parse_args()

    df = pd.read_csv(args.data)
    df["text"] = df["text"].astype(str)
    df["label"] = df["label"].astype(str)

    X_train, X_test, y_train, y_test = train_test_split(
        df["text"], df["label"],
        test_size=args.test_size,
        random_state=args.random_state,
        stratify=df["label"] if df["label"].nunique() > 1 else None
    )

    pipeline: Pipeline = Pipeline(steps=[
        ("vect", CountVectorizer(lowercase=True, ngram_range=(1, 2), stop_words="english")),
        ("clf", LogisticRegression(max_iter=200))
    ])

    pipeline.fit(X_train, y_train)

    preds = pipeline.predict(X_test)
    acc = accuracy_score(y_test, preds)

    print(f"Test Accuracy: {acc:.2f}")
    print("\nClassification Report:")
    print(classification_report(y_test, preds))

    dump(pipeline, args.model_out)
    print(f"\nSaved model to: {args.model_out}")


if __name__ == "__main__":
    main()
